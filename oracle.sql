CREATE SEQUENCE  BOARD_SEQ
			START WITH 1
			INCREMENT by 1
			NOMAXVALUE
			nocache;

create table board(
	num number primary key,
	writer varchar2 (20),
    email varchar2(50),
    subject varchar2(50),
    password varchar2(10),
    reg_date  Date,
	ref number,
    re_step number,
    re_level  number,
	readcount number,
    content  varchar2(3000)	
);	

CREATE SEQUENCE ½ÃÄö½º1;


SELECT ½ÃÄö½º1.NEXTVAL FROM DUAL;

SELECT BOARD_SEQ.CURRVAL FROM DUAL;

DROP SEQUENCE BOARD_SEQ;

