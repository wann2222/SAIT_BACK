use ssafytrip;
DROP TABLE IF EXISTS members;
-- member 테이블 생성하고 id컬럼은 PK로 설정.  
create table members(
	`id` int not null auto_increment,
    name varchar(100) not null,
    email varchar(100) not null unique,
	password varchar(100) not null unique,
    address varchar(100) not null,
    constraint members_PK primary key(`id`)
    );
-- 초기에 6명 인원을 회원으로 설정.
insert into members (name, email, password, address) values 
('김시온', "a@a", '1', "경기 안양시 만안구 안양동 618"),
('김지웅', "b@b", '2', "강원 강릉시 교동 1071"),
 ('박시온', "c@c", '3', "부산 수영구 남천동 148-4"),
 ('박지웅', "d@d", '4',"대구 달서구 대천동 1042"),
('최시온', 'e@e', '5', "대전 동구 천동 228-9"),
('최지웅', 'f@f','6', "제주 제주시 외도일동 560-1");


drop table if exists plan;
-- plan 테이블 설정. plan 테이블은 member 회원이 여행 계획을 선택하고 설정하기 위한 정보를 저장하기 위한 테이블. 이때 id를 FK로 설정.
create table plan(
	pid int not null auto_increment,
	id int not null ,
    name varchar(100) not null,
	start datetime,
    end datetime,
    
    primary key (pid),
    constraint fk_id foreign key(id) references members(id) on delete cascade
);
-- 
insert into plan (id,name, start, end) values
(1,'서울',"2018-01-01","2018-01-10"),
(1,'광주',"2019-04-03","2019-04-14"),
(2,'대전',"2023-12-14","2023-12-16"),
(1,'부산',"2018-01-01","2018-01-10"),
(2,'대구',"2018-01-01","2018-01-10"),
(3,'서울',"2018-01-01","2018-01-10"),
(3,'전주',"2018-01-01","2018-01-10");

-- select p.id, p.name, p.start, p.end
-- from plan p, members m
-- where p.id = 1 and m.id = 1;



drop table if exists planDetail;
-- planDetail테이블 설정. 각 plan별로 방문할 여행지, 순서를 저장
create table planDetail(
	idx int not null auto_increment,
	pid int not null ,
    ano int not null,
    seq int not null,
    
    primary key (idx),
    constraint fk_pid foreign key(pid) references plan(pid) on delete cascade,
    constraint fk_ano foreign key(ano) references attractions(no) on delete cascade
);


DROP TABLE IF EXISTS hotplace;
-- 핫플레이스 테이블 생성. 등록한 사람, 등록한 관광지, 다녀온 날짜, 장소유형, 코멘트
create table hotplace(
	`hid` int not null auto_increment,
    `mid` int not null,
    `title` varchar(100) not null,
    `date` datetime DEFAULT NULL,
	`content_type_id` int DEFAULT NULL comment '콘텐츠타입',
    `image` varchar(100) DEFAULT NULL comment '이미지경로',
	`x` decimal(25,10) DEFAULT NULL comment 'utm-k x',
	`y` decimal(25,10) DEFAULT NULL comment 'utm-k y',
    
    constraint hotplace_PK primary key(`hid`),
    constraint fk_hot_member_id foreign key(mid) references members(id) on delete cascade,
    CONSTRAINT `hotplace_typeid_to_types_typeid_fk`
    FOREIGN KEY (`content_type_id`)
    REFERENCES `ssafytrip`.`contenttypes` (`content_type_id`)
    );

insert into hotplace (title,mid, date, content_type_id, image,x,y) values 
('싸피공원', 1, '2022-01-01','12','http://tong.visitkorea.or.kr/cms/resource/45/1081545_image2_1.jpg',2000000,1000000),
('챔피언스필드', 2, '2020-01-01','14','http://tong.visitkorea.or.kr/cms/resource/31/2439631_image2_1.jpg',600000,200000),
 ('단골집', 3, '2018-01-01','15','http://tong.visitkorea.or.kr/cms/resource/39/2439639_image2_1.jpg',600000,200000);


drop table if exists posts;

create table posts(
	`no` int not null auto_increment,
    `mid` int not null,
	`title` varchar(100) not null,
    `content` text not null,
    `date` datetime DEFAULT NULL,
    
    primary key(`no`),
    constraint fk_post_member_id foreign key(`mid`) references members(`id`) on delete cascade
    );
    
insert into posts(mid, title, content, date) values
(1, '안녕하세요', '안녕하세요 반갑습니다', '2025-03-28'),
(2, '안녕하세요2', '안녕하세요 반갑습니다', '2024-03-28'),
(2, '안녕하세요3', '안녕하세요 반갑습니다', '2023-03-28'),
(1, '안녕하세요4', '안녕하세요 반갑습니다', '2022-03-28'),
(3, '안녕하세요5', '안녕하세요 반갑습니다', '2021-03-28'),
(3, '안녕하세요6', '안녕하세요 반갑습니다', '2020-03-28'),
(4, '안녕하세요7', '안녕하세요 반갑습니다', '2019-03-28'),
(1, '안녕하세요8', '안녕하세요 반갑습니다', '2018-03-28'),
(2, '안녕하세요9', '안녕하세요 반갑습니다', '2017-03-28'),
(3, '안녕하세요10', '안녕하세요 반갑습니다', '2016-03-28');

