#!/bin/bash
set -e
#检查安装命令是否在执行，如果执行中则跳过
echo "数据库开始装板"


#home-db装板

#先装板DDl
ddlFloder=`find /home/ldd/mysql/tmp/ -type d -name "DDL"`
for dir in $ddlFloder; do
  ddlFile=`find "$dir" -type f -name "*.sql"`
  for ddl in $ddlFile; do
    echo "开始执行: $ddl"
    mysql --defaults-extra-file=./my.password < $ddl
  done
done

#后装板DMl
# dmlFiles=
# for dir in $(find /home/ldd/mysql/tmp/ -type d -name "DML"); do
  # for file in $(find $dir -type f -name "*.sql"); do
   # dmlFiles+=("$file")
  # done
# done
# for i in "${dmlFiles[@]}"; do
 # echo "开始执行: $i"
 # mysql -u root -p ldd63.63. < $i
# done


echo "数据库装版完成"

#}

