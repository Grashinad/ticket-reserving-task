create table users
(
  id bigint PRIMARY KEY AUTO_INCREMENT,
  name varchar(255) not null,
  email varchar(255) not null
);

create table trains
(
  id bigint PRIMARY KEY AUTO_INCREMENT,
  date date not null,
  destination varchar(255) not null,
  capacity int not null,
  tickets int not null
);

create table tickets
(
  id bigint PRIMARY KEY AUTO_INCREMENT,
  user_id bigint,
  train_id bigint
);