CREATE TABLE IF NOT EXISTS "track" (
    "id" SERIAL PRIMARY KEY,
    "creator_id" INT NULL,
    "name" VARCHAR(255) NULL,
    "description" VARCHAR(255) NULL,
    "start_longitude" DOUBLE PRECISION NOT NULL,
    "start_latitude" DOUBLE PRECISION NOT NULL,
    CONSTRAINT "fk_user"
        FOREIGN KEY ("creator_id")
        REFERENCES "_user" ("id")
);

CREATE TABLE IF NOT EXISTS "point" (
    "id" SERIAL PRIMARY KEY,
    "longitude" DOUBLE PRECISION NOT NULL,
    "latitude" DOUBLE PRECISION NOT NULL,
    "track_id" INT NOT NULL,
    "order" INT NOT NULL,
    CONSTRAINT "fk_track"
        FOREIGN KEY ("track_id")
        REFERENCES "track" ("id")
);

CREATE TABLE IF NOT EXISTS "track_run" (
    "id" SERIAL PRIMARY KEY,
    "track_id" INT NOT NULL,
    "user_id" INT NOT NULL,
    "start_date" TIMESTAMP NOT NULL,
    "end_date" TIMESTAMP NOT NULL,
    "duration" TIME NOT NULL,
    CONSTRAINT "fk_track"
        FOREIGN KEY ("track_id")
        REFERENCES "track" ("id"),
    CONSTRAINT "fk_user"
            FOREIGN KEY ("user_id")
            REFERENCES "_user" ("id")
);