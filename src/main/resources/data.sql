CREATE TABLE IF NOT EXISTS spots_finder1.oauth_access_token ( token_id VARCHAR(255), token LONG VARBINARY, authentication_id VARCHAR(255) PRIMARY KEY, user_name VARCHAR(255), client_id VARCHAR(255), authentication LONG VARBINARY, refresh_token VARCHAR(255) );
CREATE TABLE IF NOT EXISTS spots_finder1.oauth_refresh_token ( token_id VARCHAR(255), token LONG VARBINARY, authentication LONG VARBINARY );
/* ALTER TABLE wrong_place_report ADD CONSTRAINT one_report_per_place_and_user unique index(place_place_id, user_user_id); */