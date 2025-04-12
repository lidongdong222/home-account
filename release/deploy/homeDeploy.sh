#!/bin/bash
#检查安装命令是否在执行，如果执行中则跳过
set -e

installDb(){
  mysqlTmpPath=/home/ldd/mysql/tmp
  mysqlReleasePath=/home/ldd/mysql/release
  #home-db装板
  rm -rf $mysqlTmpPath/*
  cp -r $workPath/db/* $mysqlTmpPath/

  #先装板DDl
  ddlFloder=`find $mysqlTmpPath -type d -name "DDL"`
  for dir in $ddlFloder; do
    ddlFile=`find "$dir" -type f -name "*.sql"`
    for ddl in $ddlFile; do
      echo "开始执行: $ddl"
      mysql --defaults-extra-file=./my.password < $ddl
    done
  done

  #后装板DML
  ddlFloder=`find $mysqlTmpPath -type d -name "DML"`
  for dir in $ddlFloder; do
    ddlFile=`find "$dir" -type f -name "*.sql"`
    for ddl in $ddlFile; do
      echo "开始执行: $ddl"
      mysql --defaults-extra-file=./my.password < $ddl
    done
  done

  cp -r  $mysqlTmpPath/* $mysqlReleasePath
  rm -rf $mysqlTmpPath/*
  echo "数据库发版完成"
}

installJar(){
  homePath=/home/ldd/jar/home
  homeBak=/home/ldd/jar/homeBak
	#home-pc-jar-备份当前版本
	cd $homePath
	mkdir -p $homeBak
	bakName=home_$(date +"%Y-%m-%d").tar
	tar -cvPf $homePath/$bakName $homePath/*.*
	mv $homePath/$bakName $homeBak
	#home-pc-jar-替换最新版本
	cp -r $workPath/jar/* $homePath
	echo "jar替换完成"
	#重启服务
	/home/ldd/jar/home/start_home.sh
	echo "home模块发版完成"
}


installUi(){
  uiPath=/home/ldd/nginx/html
  uiBackPath=/home/ldd/nginx/htmlBak
	#home-pc-ui备份当前版本
	cd $uiPath
	mkdir -p $uiBackPath
	bakName=homeui_$(date +"%Y-%m-%d").tar
	tar -cvPf $uiPath/$bakName $uiPath/*
	mv $uiPath/$bakName $uiBackPath
	#home-pc-ui-替换最新版本
	rm -rf $uiPath/*
	cp -r $workPath/dist/* $uiPath
	#nginx/不需要重启服务
	echo "ui模块发版完成"
}


#检查部署文件是否上传
preparePath=/home/ldd/release/prepare
workPath=/home/ldd/release/work
backPath=/home/ldd/release/bak

mkdir -p $preparePath
mkdir -p $workPath
mkdir -p $backPath

if [ -z "$(ls -A $preparePath)" ]; then
  echo "$preparePath 安装包为空，流程结束"
  exit 0
fi

#解压
rm -rf $workPath/*
tar -xvf $preparePath/home*.tar -C $workPath
installDb
installJar
installUi

#版本文件备份
mv -f $preparePath/*  $backPath
rm -rf $workPath

echo "本期版本发版完成"


