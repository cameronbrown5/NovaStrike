package me.thecamzone.Utils;

import com.mongodb.ConnectionString;
import com.mongodb.MongoClientSettings;
import com.mongodb.WriteConcern;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.ReplaceOptions;
import com.mongodb.lang.Nullable;
import me.thecamzone.Utils.task.TaskAsync;
import org.bson.Document;

public class MongoDBDataManager implements AutoCloseable {

    private final String host;
    private final int port;
    private final String username;
    private final String password;
    private final String databaseName;
    private final String collectionName;

    public MongoDBDataManager(String host, int port, String username, String password, String databaseName, String collectionName) {
        this.host = host;
        this.port = port;
        this.username = username;
        this.password = password;
        this.databaseName = databaseName;
        this.collectionName = collectionName;

        connect();
    }

    private MongoClient mongoClient;
    private MongoCollection<Document> playerProfilesCollection;
    private boolean connected = false;

    private void connect() {
        String connectionString = String.format("mongodb://%s:%s@%s:%d/%s", username, password, host, port, databaseName);
        ConnectionString connString = new ConnectionString(connectionString);

        MongoClientSettings clientSettings = MongoClientSettings.builder()
                .applyConnectionString(connString)
                .writeConcern(WriteConcern.ACKNOWLEDGED)
                .applyToConnectionPoolSettings(builder -> builder.maxSize(100).minSize(10))
                .build();

        this.mongoClient = MongoClients.create(clientSettings);
        MongoDatabase database = this.mongoClient.getDatabase(databaseName);
        this.playerProfilesCollection = database.getCollection(collectionName);

        try {
            mongoClient.listDatabaseNames();
            connected = true;
        } catch (Exception e) {
            connected = false;
        }
    }


    public void reconnect() {
        if (mongoClient != null)
            mongoClient.close();

        connect();
    }

    public boolean isNotConnected() {
        return !connected;
    }

    public void save(String documentID, Document document) {
        if (isNotConnected()) return;

        new TaskAsync(() -> playerProfilesCollection.replaceOne(new Document("_id", documentID), document, new ReplaceOptions().upsert(true)));
    }

    /**
     * Should be called async.
     */
    @Nullable
    public Document load(String documentID) {
        if (isNotConnected()) return null;

        if (Thread.currentThread() == MinecraftServer.getServer().serverThread){
            ErrorReporter.reportToConsole(true, false, "MongoDB option not being called async. Calls to load data should be async.");
        }

        try {
            Document query = new Document("_id", documentID);
            return playerProfilesCollection.find(query).first();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }


    @Override
    public void close() {
        if (mongoClient != null)
            mongoClient.close();
    }


    private class EXAMPLE_SAVING_LOADING_A_CLASS {

        MongoDBDataManager mongoDBDataManager = null; //A manager that1s created elsewhere to save the load the data.

        int a;
        int b;
        boolean c;
        CustomObject d;

        public void loadFromDatabase() {
            new TaskAsync(() -> {
                //Load the document from the database.
                Document document = mongoDBDataManager.load("ID FOR THIS DATA");

                //If there is no connection to the database
                if (document == null) return;

                a = document.getInteger("a", 3);
                b = document.getInteger("b", 5);
                c = document.getBoolean("c", false);
                d = document.get("d", new CustomObject());
            });
        }

        public void saveToDatabase() {
            mongoDBDataManager.save("ID FOR THIS DATA", new Document()
                    .append("a", a)
                    .append("b", b)
                    .append("c", c)
                    .append("d", d)
            );
        }


        class CustomObject {
        }

        ;
    }
}