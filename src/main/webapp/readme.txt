1/ ��װMS SQL, ���ú�SA���û���������.  �ڷ��������������д�TCP/IPЭ��,��1433�˿�. 
2/ ��SQL SERVER���½����ݿ�LOTTERY.
   ִ�����½ű�.
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

insert into menuteims(id, leaf, menutext, parentid, url) values (	1000,0,'��������ά��',0,NULL)
insert into menuteims(id, leaf, menutext, parentid, url) values (	1010,1,'����ά��',1000,'property.do?propertyType=1')
insert into menuteims(id, leaf, menutext, parentid, url) values (	1020,1,'����ά��',1000,'property.do?propertyType=2')
insert into menuteims(id, leaf, menutext, parentid, url) values (	1030,1,'��˾ά��',1000,'property.do?propertyType=5')
insert into menuteims(id, leaf, menutext, parentid, url) values (	1040,1,'��С�̿�',1000,'property.do?propertyType=4')
insert into menuteims(id, leaf, menutext, parentid, url) values (	1050,1,'�����̿�',1000,'property.do?propertyType=3')
insert into menuteims(id, leaf, menutext, parentid, url) values (	2000,0,'�̿�����ά��',0,null)
insert into menuteims(id, leaf, menutext, parentid, url) values (	2010,1,'�̿���������',2000,'match.do')
insert into menuteims(id, leaf, menutext, parentid, url) values (	3000,0,'�̿����ݷ���',0,null)
insert into menuteims(id, leaf, menutext, parentid, url) values (	3010,1,'ŷ��2.37�̿ڷ���',3000,'analyze1.do')
insert into menuteims(id, leaf, menutext, parentid, url) values (	3020,1,'2.37������/�Ͷ����ݷ���',3000,'analyze2.do')



insert into properttype (id, text) values (1,'����')
insert into properttype (id, text) values (2,'����')
insert into properttype (id, text) values (3,'�����̿�')
insert into properttype (id, text) values (4,'��С�̿�')
insert into properttype (id, text) values (5,'��˾')

insert into property (id2, text, propertytype_id) values (9, '����', 5)
insert into property (id2, text, propertytype_id) values (14, 'Τ��', 5)
insert into property (id2, text, propertytype_id) values (4, '����', 5)
insert into property (id2, text, propertytype_id) values (15, 'SSP', 5)
insert into property (id2, text, propertytype_id) values (24, '12bet', 5)
insert into property (id2, text, propertytype_id) values (1, '����', 5)
insert into property (id2, text, propertytype_id) values (19, 'InterW', 5)
insert into property (id2, text, propertytype_id) values (12, '��ʤ', 5)
insert into property (id2, text, propertytype_id) values (18, 'Eurobet', 5)
insert into property (id2, text, propertytype_id) values (7, 'SNAI', 5)
insert into property (id2, text, propertytype_id) values (3, '�ӣ�', 5)
insert into property (id2, text, propertytype_id) values (2, '����', 5)
insert into property (id2, text, propertytype_id) values (8, 'BET365', 5)
insert into property (id2, text, propertytype_id) values (17, '���', 5)
insert into property (id2, text, propertytype_id) values (23, '�𱦲�', 5)
insert into property (id2, text, propertytype_id) values (5, '�ƶ�', 5)
insert into property (id2, text, propertytype_id) values (29, '������', 5)
insert into property (id2, text, propertytype_id) values (31, '����', 5)

3/ ��װTOMCAT 6.0.20.
4/ ��TOMCAT�İ�װĿ¼, �ҵ������webappsĿ¼, ��������ѹ������LOTTERYĿ¼, ����WEBAPPS��.
   $TOMCAT_HOME\WEBAPPS\LOTTERY....
5/ �޸�$TOMCAT_HOME\WEBAPPS\LOTTERY\WEB-INF�����applicationContext.xml�ļ�, �����ݿ�������ȷ����.
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

                 <value>���ݿ�����</value>

           </property>

      </bean>

6/ ���ҵĵ���,����->��������, ����APACHE TOMCAT. ����.

7/ ��IE, ��http://localhost:8080/Lottery, (ע��, һ��Ҫ�������ַ�Ĵ�Сд��.)
   �û���: admin
   ����: 123456
   
8/ �������������������, �ǵ��ڱ����ķ���ǽ�ϰ�8080�˿ڴ�.