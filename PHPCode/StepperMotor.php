<?php
	# Most of this PHP code taken from: http://sampleprogramz.com/android/mysqldb.php

	$host='xxxx';
	$uname='xxxx';
	$pwd='xxxx';
	$db="xxxx";

	$con = mysql_connect($host,$uname,$pwd) or die("connection failed");
	mysql_select_db($db,$con) or die("db selection failed");
	 
	$pin1=$_REQUEST['pin1'];
	$pin2=$_REQUEST['pin2'];
	$pin3=$_REQUEST['pin3'];
	$pin4=$_REQUEST['pin4'];
	$speed=$_REQUEST['speed'];
	$direction=$_REQUEST['direction'];

	$flag['code']=0;

	# Insert Pin1 value
	if($r=mysql_query("UPDATE StepperMotor SET Pin1 = '$pin1' WHERE id = '1' ",$con))
	{
		$flag['code']=1;
		echo"hi";
	}
	
	# Insert Pin2 value
	if($r=mysql_query("UPDATE StepperMotor SET Pin2 = '$pin2' WHERE id = '1' ",$con))
	{
		$flag['code']=1;
		echo"hi";
	}
	
	# Insert Pin3 value
	if($r=mysql_query("UPDATE StepperMotor SET Pin3 = '$pin3' WHERE id = '1' ",$con))
	{
		$flag['code']=1;
		echo"hi";
	}
	
	# Insert Pin4 value
	if($r=mysql_query("UPDATE StepperMotor SET Pin4 = '$pin4' WHERE id = '1' ",$con))
	{
		$flag['code']=1;
		echo"hi";
	}
	
	# Insert Speed value
	if($r=mysql_query("UPDATE StepperMotor SET Speed = '$speed' WHERE id = '1' ",$con))
	{
		$flag['code']=1;
		echo"hi";
	}
	
	# Insert Direction value
	if($r=mysql_query("UPDATE StepperMotor SET Direction = '$direction' WHERE id = '1' ",$con))
	{
		$flag['code']=1;
		echo"hi";
	}
	
	if($r=mysql_query("UPDATE StepperMotor SET readIndicator = '1' WHERE id = '1' ",$con))
	{
		$flag['code']=1;
		echo"hi";
	}

	# Need to implement check for successful DB insertion
	print(json_encode($flag));
	mysql_close($con);
?>