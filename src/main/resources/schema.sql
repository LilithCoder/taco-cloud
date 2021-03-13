--定义数据库的schema

--保存着原料信息
create table if not exists Ingredient (
    id varchar(4) not null,
    name varchar(25) not null,
    type varchar(10) not null
);

--保存着关于 taco 设计的重要信息
create table if not exists Taco (
    id identity,
    name varchar(50) not null,
    createdAt timestamp not null
);

--包含 Taco 表中每一行的一个或多行数据(一个taco可能有多种ingredient，也可能只有一种ingredient)
--将 Taco 映射到该 Taco 的 Ingredient
create table if not exists Taco_Ingredients (
    taco bigint not null,
    ingredient varchar(4) not null,
    foreign key (taco) references Taco(id),
    foreign key (ingredient) references Ingredient(id)
);

--保存着重要的订单细节
create table if not exists Taco_Order (
    id identity,
    deliveryName varchar(50) not null,
    deliveryStreet varchar(50) not null,
    deliveryCity varchar(50) not null,
    deliveryState varchar(2) not null,
    deliveryZip varchar(10) not null,
    ccNumber varchar(16) not null,
    ccExpiration varchar(5) not null,
    ccCVV varchar(3) not null,
    placedAt timestamp not null
);

--包含 Taco_Order 表中的每一行的一个或多行数据，
--将 Order 映射到 Order 中的Tacos
create table if not exists Taco_Order_Tacos (
    tacoOrder bigint not null,
    taco bigint not null,
    foreign key (tacoOrder) references Taco_Order(id),
    foreign key (taco) references Taco(id)
);