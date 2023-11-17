Mysql 로컬 설치시 Access Denied 발생했을 때 패스워드 변경

```bash
$ sudo mysql -u root
mysql> use mysql;
mysql> select user,host,plugin FROM mysql.user;
mysql> set password for 'root'@'localhost'=password('test1357');
mysql> flush previleges;
```

