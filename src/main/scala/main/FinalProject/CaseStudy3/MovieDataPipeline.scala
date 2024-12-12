package main.FinalProject.CaseStudy3

import org.apache.log4j.{Level, Logger}
import org.apache.spark.sql.{DataFrame, SparkSession}
import org.apache.spark.sql.functions._
import org.apache.spark.sql.expressions.Window

object MovieDataPipeline {
  val spark: SparkSession = SparkSession.builder()
    .appName("MovieLens Analytics Pipeline")
    .config("spark.hadoop.fs.gs.impl", "com.google.cloud.hadoop.fs.gcs.GoogleHadoopFileSystem")
    .config("spark.hadoop.fs.AbstractFileSystem.gs.impl", "com.google.cloud.hadoop.fs.gcs.GoogleHadoopFS")
    .config("spark.hadoop.google.cloud.auth.service.account.enable", "true")
    .config("spark.hadoop.google.cloud.auth.service.account.json.keyfile", "/Users/navadeep/spark-gcs-key.json")
    .config("spark.ui.showConsoleProgress", "false")
    .master("local[*]")
    .getOrCreate()

  Logger.getLogger("org").setLevel(Level.WARN)
  Logger.getLogger("akka").setLevel(Level.WARN)
  Logger.getLogger("org.apache.spark").setLevel(Level.WARN)


  private def loadDataset(filePath: String, datasetName: String): DataFrame = {
    println(s"Loading $datasetName dataset from: $filePath")
    val df = spark.read.option("header", "true").csv(filePath)
    println(s"Displaying first 5 rows of $datasetName dataset:")
    df.show(5, truncate = false)
    println(s"Schema of $datasetName dataset:")
    df.printSchema()
    println(s"$datasetName dataset count: ${df.count()}")
    df
  }

  private def prepareRatings(ratings: DataFrame): DataFrame = {
    println("Preparing ratings data...")
    println("Raw Ratings:")
    ratings.show(5)
    ratings.withColumn("ratingDate",
        unix_timestamp(col("timestamp"), "yyyy-MM-dd HH:mm:ss").cast("timestamp"))
      .na.drop()
  }


  private def transformMovies(movies: DataFrame): DataFrame = {
    println("Transforming movies data...")
    movies.na.drop().withColumn("genre", explode(split(col("genres"), "\\|")))
  }

  private def cleanTags(tags: DataFrame): DataFrame = {
    println("Cleaning tags data...")
    tags.na.drop()
  }

  private def integrateData(ratings: DataFrame, movies: DataFrame, tags: DataFrame, links: DataFrame): DataFrame = {
    println("Integrating datasets...")
    val combinedDF = ratings.join(movies, "movieId")
    val taggedDF = combinedDF.join(tags, Seq("movieId", "userId"), "left")
    taggedDF.join(links, Seq("movieId"), "left")
  }

  private def checkNulls(df: DataFrame, name: String, columns: Seq[String]): Unit = {
    columns.foreach { colName =>
      val nullCount = df.filter(df(colName).isNull).count()
      println(s"Null check in $name - Column: $colName - Null Count: $nullCount")
    }
  }

  private def calculateMovieStats(data: DataFrame): DataFrame = {
    data.groupBy("movieId", "title", "genre")
      .agg(
        avg("rating").alias("averageRating"),
        count("rating").alias("ratingCount"),
        variance("rating").alias("ratingVariance")
      )
  }

  private def topRatedMovies(stats: DataFrame): DataFrame = {
    stats.filter("ratingCount >= 50").orderBy(desc("averageRating")).limit(10)
  }

  private def aggregateByGenre(data: DataFrame): DataFrame = {
    print("Aggregate bY Genre")
    data.show(5)
    data.groupBy("genre")
      .agg(
        avg("rating").alias("avgGenreRating"),
        count("rating").alias("totalRatingsByGenre")
      )
  }

  private def ratingsPerYear(data: DataFrame): DataFrame = {
    data.groupBy(year(col("ratingDate")).alias("year"))
      .agg(avg("rating").alias("yearlyAverage"))
  }

  private def rankMoviesByGenre(stats: DataFrame): DataFrame = {
    val window = Window.partitionBy("genre").orderBy(desc("ratingCount"), desc("averageRating"))
    stats.withColumn("rank", rank().over(window))
  }

  private def createGenrePivot(data: DataFrame): DataFrame = {
    data.groupBy(year(col("ratingDate"))).pivot("genre").agg(avg("rating"))
  }

  private def findAnomalies(data: DataFrame): DataFrame = {
    data.groupBy("genre", "movieId")
      .agg(stddev("rating").alias("ratingStdDev"))
      .filter("ratingStdDev > 2.5")
  }

  private def groupUsers(data: DataFrame): DataFrame = {
    data.groupBy("userId")
      .agg(count("rating").alias("totalRatings"), avg("rating").alias("averageRating"))
  }

  private def mostFrequentTags(tags: DataFrame): DataFrame = {
    tags.groupBy("movieId").count().orderBy(desc("count"))
  }

  private def persistData(df: DataFrame, path: String, format: String = "parquet"): Unit = {
    println(s"Saving data to: $path as $format")
    df.write.mode("overwrite").format(format).save(path)
  }

  private def generateReports(data: DataFrame): Unit = {
    println("Generating reports and visualizations...")
    val genreTrends = aggregateByGenre(data)
    genreTrends.show(truncate = false)

    val userActivity = groupUsers(data)
    userActivity.show(truncate = false)

    val topMovies = topRatedMovies(calculateMovieStats(data))
    topMovies.show(truncate = false)

    val yearlyRatings = ratingsPerYear(data)
    yearlyRatings.show(truncate = false)

    val rollingAvg = data.withColumn("year", year(col("ratingDate"))).groupBy("year")
      .agg(avg("rating").alias("rollingAverage")).orderBy("year")

    rollingAvg.show(truncate = false)
    println("Reports and visualizations generated successfully!")
  }

  def main(args: Array[String]): Unit = {
    val ratings = prepareRatings(loadDataset("/Users/navadeep/Downloads/Work/Assignments/Datasets/movieDataset/rating.csv", "Ratings"))
    val movies = transformMovies(loadDataset("/Users/navadeep/Downloads/Work/Assignments/Datasets/movieDataset/movie.csv", "Movies"))
    val tags = cleanTags(loadDataset("/Users/navadeep/Downloads/Work/Assignments/Datasets/movieDataset/tag.csv", "Tags"))
    val links = loadDataset("/Users/navadeep/Downloads/Work/Assignments/Datasets/movieDataset/link.csv", "Links")

    ratings.show(5)
    movies.show(5)
    tags.show(5)
    links.show(5)

    // Check for nulls
    checkNulls(ratings, "Ratings", Seq("movieId", "userId"))
    checkNulls(movies, "Movies", Seq("movieId"))
    checkNulls(tags, "Tags", Seq("movieId", "userId"))
    checkNulls(links, "Links", Seq("movieId"))

    val fullData = integrateData(ratings, movies, tags, links)
    println("Full Data:")
    fullData.show(5)

    persistData(calculateMovieStats(fullData), "gs://task-dataset-bucket/FinalProject/CaseStudy3/movies_stats")
    persistData(topRatedMovies(calculateMovieStats(fullData)), "gs://task-dataset-bucket/FinalProject/CaseStudy3/top_movies.json", "json")
    persistData(aggregateByGenre(fullData), "gs://task-dataset-bucket/FinalProject/CaseStudy3/genre_stats.json", "json")
    persistData(ratingsPerYear(fullData), "gs://task-dataset-bucket/FinalProject/CaseStudy3/yearly_ratings.json", "json")
    persistData(rankMoviesByGenre(calculateMovieStats(fullData)), "gs://task-dataset-bucket/FinalProject/CaseStudy3/ranked_movies.json", "json")
    persistData(createGenrePivot(fullData), "gs://task-dataset-bucket/FinalProject/CaseStudy3/genre_pivot.json", "json")
    persistData(findAnomalies(fullData), "gs://task-dataset-bucket/FinalProject/CaseStudy3/anomalies.json", "json")
    persistData(groupUsers(fullData), "gs://task-dataset-bucket/FinalProject/CaseStudy3/user_groups.json", "json")
    persistData(mostFrequentTags(tags), "gs://task-dataset-bucket/FinalProject/CaseStudy3/top_tags.json", "json")

    generateReports(fullData)
    println("Data Pipeline Execution Completed Successfully!")
  }
}