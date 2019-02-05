package edu.northeastern.ccs.im.dao;

import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;

import org.bson.Document;

import java.util.Objects;
import java.util.logging.Logger;

/**
 * BaseMongo class to establish a connection to the MongoDB database. In addition, provides a
 * getCollection and isClosed method.
 *
 * @author Akshat Shukla
 * @author Matthew Lazarcheck
 */
abstract class BaseMongo {
  private String dburl = "mongodb://root:password1@ds255253.mlab.com:55253/cs5500";
  private MongoClientURI mongoURI = new MongoClientURI(dburl);
  private MongoDatabase db = null;
  private MongoClient mongoClient = null;
  private static final Logger logger = Logger.getLogger(BaseMongo.class.getName());
  String collection = "";
  private boolean closed = true;

  /**
   * Connect to MongoDB database.
   *
   * @return new MongoDB database.
   */
  private MongoDatabase connectToMongo() {
    try {
      mongoClient = new MongoClient(mongoURI);
      db = mongoClient.getDatabase(Objects.requireNonNull(mongoURI.getDatabase()));
    } catch (Exception e) {
      logger.info(e.toString());
    }
    return db;
  }

  /**
   * Get MongoDB collection with the provided collection name.
   *
   * @param collection the name of the collection to get.
   * @return the MongoCollection.
   */
  public MongoCollection<Document> getCollection(String collection) {
    MongoCollection<Document> dbCollection = null;
    try {
      if (db == null) {
        db = connectToMongo();
      }
      dbCollection = db.getCollection(collection);
    } catch (Exception e) {
      logger.info(e.toString());
    }
    closed = false;
    return dbCollection;
  }

  /**
   * Determine whether the collection is open or closed.
   *
   * @return true if the collection is open, false if the collection is closed.
   */
  public boolean isClosed() {
    return closed;
  }
}
