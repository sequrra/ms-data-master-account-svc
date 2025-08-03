-- Drop UNIQUE constraint on acct_name
ALTER TABLE "ms-data-master-account".t_acct
DROP CONSTRAINT IF EXISTS t_acct_acct_name_key;
