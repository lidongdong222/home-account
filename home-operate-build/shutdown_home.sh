homepid=`ps -ef|grep home.jar |grep -v "grep" |awk '{print $2}'`
if [ -z "$homepid" ]; then
  echo "home.jar is not running"
else
  kill -9 $homepid
  echo "home.jar run pid is $homepid ,has killed."
fi

echo "home.jar has shutdown!"
