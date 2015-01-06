<?php
	# Most of this PHP code taken from: http://sampleprogramz.com/android/mysqldb.php

	$host='xxxx';
	$uname='xxxx';
	$pwd='xxxx!';
	$db="xxxx";

	$con = mysql_connect($host,$uname,$pwd) or die("connection failed");
	mysql_select_db($db,$con) or die("db selection failed");
	 
	$pin=$_REQUEST['pin'];
	$intensity=$_REQUEST['intensity'];
	$length=$_REQUEST['length'];

	$flag['code']=0;

	# Insert ArduinoPin value
	if($r=mysql_query("UPDATE LED SET ArduinoPin = '$pin' WHERE id = '1' ",$con))
	{
		$flag['code']=1;
		echo"hi";
	}
	
	# Insert LEDIntensity value
	if($r=mysql_query("UPDATE LED SET LEDIntensity = '$intensity' WHERE id = '1' ",$con))
	{
		$flag['code']=1;
		echo"hi";
	}
	
	# Insert Length value
	if($r=mysql_query("UPDATE LED SET Length = '$length' WHERE id = '1' ",$con))
	{
		$flag['code']=1;
		echo"hi";
	}
	
	# Insert readIndicator value
	if($r=mysql_query("UPDATE LED SET readIndicator = '1' WHERE id = '1' ",$con))
	{
		$flag['code']=1;
		echo"hi";
	}

	# Need to implement check for successful DB insertion
	print(json_encode($flag));
	mysql_close($con);
?>