ALTER TABLE measurements ADD COLUMN bill_id INT;
ALTER TABLE measurements ADD CONSTRAINT FK_measurements_bills FOREIGN KEY (bill_id) REFERENCES bills(id) ON DELETE CASCADE;