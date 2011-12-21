1/ 安装MS SQL, 设置好SA的用户名和密码.  在服务器网络配置中打开TCP/IP协议,和1433端口. 
2/ 在SQL SERVER中新建数据库LOTTERY.
   执行以下脚本.
     create table "lottery"."dbo"."MenuItems"(
        "ID" int not null,
       "defQueryString" varchar(255) null,
       "leaf" tinyint not null,
       "menuText" varchar(255) null,
       "parentID" int not null,
       "queryString" varchar(255) null,
       "reportId" varchar(255) null,
       "url" varchar(255) null,
        constraint "PK__MenuItems__7C8480AE" primary key ("ID")
    )
go

    create unique index "PK__MenuItems__7C8480AE" on "lottery"."dbo"."MenuItems"("ID")
go



    create table "lottery"."dbo"."PropertyType"(
        "id" int not null,
       "text" varchar(255) null,
        constraint "PK__PropertyType__0AD2A005" primary key ("id")
    )
go

    create unique index "PK__PropertyType__0AD2A005" on "lottery"."dbo"."PropertyType"("id")
go

insert into menuteims(id, leaf, menutext, parentid, url) values (	1000,0,'基础数据维护',0,NULL)
insert into menuteims(id, leaf, menutext, parentid, url) values (	1010,1,'赛事维护',1000,'property.do?propertyType=1')
insert into menuteims(id, leaf, menutext, parentid, url) values (	1020,1,'队伍维护',1000,'property.do?propertyType=2')
insert into menuteims(id, leaf, menutext, parentid, url) values (	1030,1,'公司维护',1000,'property.do?propertyType=5')
insert into menuteims(id, leaf, menutext, parentid, url) values (	1040,1,'大小盘口',1000,'property.do?propertyType=4')
insert into menuteims(id, leaf, menutext, parentid, url) values (	1050,1,'亚赔盘口',1000,'property.do?propertyType=3')
insert into menuteims(id, leaf, menutext, parentid, url) values (	2000,0,'盘口数据维护',0,null)
insert into menuteims(id, leaf, menutext, parentid, url) values (	2010,1,'盘口数据输入',2000,'match.do')
insert into menuteims(id, leaf, menutext, parentid, url) values (	3000,0,'盘口数据分析',0,null)
insert into menuteims(id, leaf, menutext, parentid, url) values (	3010,1,'欧赔2.37盘口分析',3000,'analyze1.do')
insert into menuteims(id, leaf, menutext, parentid, url) values (	3020,1,'2.37赔率主/客队数据分析',3000,'analyze2.do')



insert into properttype (id, text) values (1,'赛事')
insert into properttype (id, text) values (2,'队伍')
insert into properttype (id, text) values (3,'亚赔盘口')
insert into properttype (id, text) values (4,'大小盘口')
insert into properttype (id, text) values (5,'公司')

insert into property (id2, text, propertytype_id) values (9, '威廉', 5)
insert into property (id2, text, propertytype_id) values (14, '韦德', 5)
insert into property (id2, text, propertytype_id) values (4, '立博', 5)
insert into property (id2, text, propertytype_id) values (15, 'SSP', 5)
insert into property (id2, text, propertytype_id) values (24, '12bet', 5)
insert into property (id2, text, propertytype_id) values (1, '澳门', 5)
insert into property (id2, text, propertytype_id) values (19, 'InterW', 5)
insert into property (id2, text, propertytype_id) values (12, '易胜', 5)
insert into property (id2, text, propertytype_id) values (18, 'Eurobet', 5)
insert into property (id2, text, propertytype_id) values (7, 'SNAI', 5)
insert into property (id2, text, propertytype_id) values (3, 'ＳＢ', 5)
insert into property (id2, text, propertytype_id) values (2, '波音', 5)
insert into property (id2, text, propertytype_id) values (8, 'BET365', 5)
insert into property (id2, text, propertytype_id) values (17, '明陞', 5)
insert into property (id2, text, propertytype_id) values (23, '金宝博', 5)
insert into property (id2, text, propertytype_id) values (5, '云鼎', 5)
insert into property (id2, text, propertytype_id) values (29, '乐天堂', 5)
insert into property (id2, text, propertytype_id) values (31, '利记', 5)

3/ 安装TOMCAT 6.0.20.
4/ 打开TOMCAT的安装目录, 找到里面的webapps目录, 将附件解压出来的LOTTERY目录, 拷进WEBAPPS中.
   $TOMCAT_HOME\WEBAPPS\LOTTERY....
5/ 修改$TOMCAT_HOME\WEBAPPS\LOTTERY\WEB-INF里面的applicationContext.xml文件, 将数据库密码正确输入.
      <bean id="dataSource" class="org.apache.commons.dbcp.BasicDataSource" destroy-method="close">

           <property name="driverClassName">

                 <value>com.microsoft.sqlserver.jdbc.SQLServerDriver</value>

            </property>

           <property name="url">

                 <value>jdbc:sqlserver://127.0.0.1:1433;databaseName=lottery</value>

           </property>

           <property name="username">

                 <value>sa</value>

           </property>

           <property name="password">

                 <value>数据库密码</value>

           </property>

      </bean>

6/ 在我的电脑,管理->服务里面, 启动APACHE TOMCAT. 服务.

7/ 用IE, 打开http://localhost:8080/Lottery, (注意, 一定要按这个地址的大小写来.)
   用户名: admin
   密码: 123456
   
8/ 如果从其他电脑联过来, 记得在本机的防火墙上把8080端口打开.