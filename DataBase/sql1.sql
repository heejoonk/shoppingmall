
/* Drop Tables */

DROP TABLE address CASCADE CONSTRAINTS;
DROP TABLE cart CASCADE CONSTRAINTS;
DROP TABLE order_detail CASCADE CONSTRAINTS;
DROP TABLE orders CASCADE CONSTRAINTS;
DROP TABLE qna CASCADE CONSTRAINTS;
DROP TABLE Member CASCADE CONSTRAINTS;
DROP TABLE product CASCADE CONSTRAINTS;
DROP TABLE worker CASCADE CONSTRAINTS;




/* Create Tables */

CREATE TABLE address
(
	zip_num varchar2(10) NOT NULL,
	sido varchar2(30) NOT NULL,
	gugun varchar2(30) NOT NULL,
	dong varchar2(100) NOT NULL,
	zip_code varchar2(30),
	bunji varchar2(30)
);


CREATE TABLE cart
(
	cseq number(5) NOT NULL,
	id varchar2(20) NOT NULL,
	pseq number(5) NOT NULL,
	quantity number(5) NOT NULL,
	result char(1) DEFAULT '1',
	indate date DEFAULT sysdate,
	PRIMARY KEY (cseq)
);


CREATE TABLE Member
(
	id varchar2(20) NOT NULL,
	pwd varchar2(20) NOT NULL,
	name varchar2(20) NOT NULL,
	phone varchar2(20) NOT NULL,
	email varchar2(50) NOT NULL,
	zip_num varchar2(10) NOT NULL,
	address1 varchar2(100) NOT NULL,
	address2 varchar2(100),
	indate date DEFAULT sysdate,
	useyn char(1) DEFAULT 'Y',
	PRIMARY KEY (id)
);


CREATE TABLE orders
(
	oseq number(5) NOT NULL,
	id varchar2(20) NOT NULL,
	indate date DEFAULT sysdate,
	PRIMARY KEY (oseq)
);


CREATE TABLE order_detail
(
	odseq number(7) NOT NULL,
	oseq number(5) NOT NULL,
	pseq number(5) NOT NULL,
	quantity number(5) NOT NULL,
	result char(1) DEFAULT '1',
	PRIMARY KEY (odseq)
);


CREATE TABLE product
(
	pseq number(5) NOT NULL,
	name varchar2(100) NOT NULL,
	kind char(1) NOT NULL,
	price1 number(7),
	price2 number(7) NOT NULL,
	price3 number(7),
	content varchar2(1000),
	image varchar2(100),
	useyn char(1),
	bestyn char(1),
	indate date DEFAULT sysdate,
	PRIMARY KEY (pseq)
);


CREATE TABLE qna
(
	qseq number(5) NOT NULL,
	id varchar2(20) NOT NULL,
	subject varchar2(100) NOT NULL,
	content varchar2(1000) NOT NULL,
	rep char(1) DEFAULT '1' NOT NULL,
	reply varchar2(1000),
	indate date DEFAULT sysdate,
	PRIMARY KEY (qseq)
);


CREATE TABLE worker
(
	id varchar2(20) NOT NULL,
	pwd varchar2(20) NOT NULL,
	name varchar2(30) NOT NULL,
	phone varchar2(20) NOT NULL,
	PRIMARY KEY (id)
);



/* Create Foreign Keys */

ALTER TABLE cart
	ADD FOREIGN KEY (id)
	REFERENCES Member (id)
;


ALTER TABLE orders
	ADD FOREIGN KEY (id)
	REFERENCES Member (id)
;


ALTER TABLE qna
	ADD FOREIGN KEY (id)
	REFERENCES Member (id)
;


ALTER TABLE order_detail
	ADD FOREIGN KEY (oseq)
	REFERENCES orders (oseq)
;


ALTER TABLE cart
	ADD FOREIGN KEY (pseq)
	REFERENCES product (pseq)
;


ALTER TABLE order_detail
	ADD FOREIGN KEY (pseq)
	REFERENCES product (pseq)
;



