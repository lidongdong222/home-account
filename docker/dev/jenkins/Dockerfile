#使用官方Jenkins镜像作为基础镜像
FROM jenkins/jenkins:lts

#切换root用户，安装必要软件包
USER root

#安装MAVEN和其他依赖
RUN apt-get update && \ 
    apt-get install -y maven && \ 
	apt-get install -y npm && \ 
	apt-get clean



#切换回jenkins用户
USER jenkins

