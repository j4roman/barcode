CREATE TABLE bc_algorithm (
    id NUMBER GENERATED BY DEFAULT ON NULL AS IDENTITY,
    name VARCHAR2(10) NOT NULL,
    in_pattern VARCHAR2(30) NOT NULL,
    out_pattern VARCHAR2(30) NOT NULL,
    description VARCHAR2(200)
);

ALTER TABLE bc_algorithm 
ADD CONSTRAINT PK_bc_algorithm 
PRIMARY KEY (id);
    
ALTER TABLE bc_algorithm 
ADD CONSTRAINT UQ_bc_algorithm 
UNIQUE (name);

CREATE TABLE action (
    id NUMBER GENERATED BY DEFAULT ON NULL AS IDENTITY,
    bc_algorithm_id NUMBER NOT NULL,
    order_num NUMBER NOT NULL,
    task VARCHAR2(10) NOT NULL,
    ind1 NUMBER,
    ind2 NUMBER,
    count NUMBER,
    description VARCHAR2(200)
);

ALTER TABLE action 
ADD CONSTRAINT PK_action 
PRIMARY KEY (id);

CREATE TABLE client (
    id NUMBER GENERATED BY DEFAULT ON NULL AS IDENTITY,
    code VARCHAR(10) NOT NULL,
    name VARCHAR2(50) NOT NULL,
    description VARCHAR(200)    
);

ALTER TABLE client 
ADD CONSTRAINT PK_client 
PRIMARY KEY (id);
    
ALTER TABLE client 
ADD CONSTRAINT UQ_client 
UNIQUE (code);

CREATE TABLE client2algorithm (
    id NUMBER GENERATED BY DEFAULT ON NULL AS IDENTITY,
    client_id NUMBER NOT NULL,
    bc_algorithm_id NUMBER NOT NULL,
    spec_value VARCHAR(15) NOT NULL
);

ALTER TABLE client2algorithm
ADD CONSTRAINT PK_client2algorithm
PRIMARY KEY (id);

ALTER TABLE client2algorithm
ADD CONSTRAINT UQ_client2algorithm
UNIQUE (client_id, bc_algorithm_id);

ALTER TABLE action
ADD CONSTRAINT FK_action_bc_algorithm
FOREIGN KEY (bc_algorithm_id) REFERENCES bc_algorithm (id);

ALTER TABLE client2algorithm
ADD CONSTRAINT FK_client2algorithm_algorithm 
FOREIGN KEY (bc_algorithm_id) REFERENCES bc_algorithm (id);

ALTER TABLE client2algorithm
ADD CONSTRAINT FK_client2algorithm_client 
FOREIGN KEY (client_id) REFERENCES client (id);