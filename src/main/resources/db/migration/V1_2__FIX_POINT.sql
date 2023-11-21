ALTER TABLE "point"
  DROP COLUMN "order";

ALTER TABLE "point"
  ADD "sequence_position" INT NOT NULL;