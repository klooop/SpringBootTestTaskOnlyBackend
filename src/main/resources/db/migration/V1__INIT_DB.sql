CREATE TABLE lords (id bigserial primary key , name varchar(100), age int);
CREATE TABLE planets (id bigserial primary key, name varchar(100),
                      isOwned boolean default false, lord_id bigint,
                       foreign key (lord_id) references lords(id) ON DELETE CASCADE);



