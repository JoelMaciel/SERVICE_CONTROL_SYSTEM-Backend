ALTER TABLE service
RENAME TO service_provided ;

ALTER TABLE service_provided
ADD COLUMN creation_date timestamp AFTER `price`;
