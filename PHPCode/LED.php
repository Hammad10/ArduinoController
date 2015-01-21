<?php
	# Most of this PHP code taken from: http://sampleprogramz.com/android/mysqldb.php

	$host='xxxx';
	$username='xxxx';
	$password='xxxx';
	$database="xxxx";

	$db = new mysqli($host, $username, $password, $database);
	
	$pin = $_REQUEST['pin'];
	$intensity = $_REQUEST['intensity'];
	$length = $_REQUEST['length'];

	$flag['code']=0;
	
	//Define all the input values as an associative array
	$inputs = array("ArduinoPin" => $pin,
					"LEDIntensity" => $intensity,
					"Length" => $length);
					
	foreach($inputs as $key => $value) {
		//Insert each value
		$q = $db->query("UPDATE `LED` SET `{$key}` = '{$value}' WHERE `id` = '1'");
		$flag['code']=1;
	}
	
	$q = $db->query("UPDATE `LED` SET `readIndicator` = '1' WHERE `id` = '1'");
	
	// Need to implement check for successful DB insertion
	print(json_encode($flag));
	
?>