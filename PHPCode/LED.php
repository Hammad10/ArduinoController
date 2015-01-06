<?php
	$host='xxxx';
	$uname='xxxx';
	$pwd='xxxx';
	$db="xxxx";

	$con = mysql_connect($host,$uname,$pwd) or die("connection failed");
	mysql_select_db($db,$con) or die("db selection failed");
	 
	$pin=$_REQUEST['pin'];
	$intensity=$_REQUEST['intensity'];
	$length=$_REQUEST['length'];

	$flag['code']=0;
//#cur.execute(" UPDATE LED SET ArduinoPin = '11', LEDIntensity = '11', Length = '11' WHERE id = '1' ") ##Inserts into id1
	//if($r=mysql_query("insert into Length values('$id','$name') ",$con))
	if($r=mysql_query("UPDATE LED SET ArduinoPin = '$pin' WHERE id = '1' ",$con))
	{
		$flag['code']=1;
		echo"hi";
	}
	if($r=mysql_query("UPDATE LED SET LEDIntensity = '$intensity' WHERE id = '1' ",$con))
	{
		$flag['code']=1;
		echo"hi";
	}
	if($r=mysql_query("UPDATE LED SET Length = '$length' WHERE id = '1' ",$con))
	{
		$flag['code']=1;
		echo"hi";
	}
	if($r=mysql_query("UPDATE LED SET readIndicator = '1' WHERE id = '1' ",$con))
	{
		$flag['code']=1;
		echo"hi";
	}

	print(json_encode($flag));
	mysql_close($con);
?>