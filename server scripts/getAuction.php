<?php
//	include 'config.php';
	$user_id=$_GET['id_user'];
	date_default_timezone_set("Asia/Kolkata");
	$time=date( 'Y-m-d H:i:s', time());
        // include db connect class
    require_once __DIR__ . '/db_connect.php';
 
    // connecting to db
    $db = new DB_CONNECT();
//	echo $time;
//	$con=mysqli_connect($IP,$user,$pass,$db);
//	echo "select `idAuction`, `location`, `start_time`, `expctd_time`, `description` from `Auction` where `idUser`=\"$user_id\" and `end_time`>=\"$time\"";
	$result=mysql_query("select `idAuction`, `location`, `start_time`, `expctd_time`, `description` from `Auction` where `idUser`=\"$user_id\" and `end_time`>=\"$time\"") or die("Error: ".mysqli_error($con));
	
	$num=mysqli_num_rows($result);

	if ($num==0)
	{
		$arr = array(
		    "isRunning" => "False", 
		);
		$output[]=$arr;
	}
	else
	{
		$arr = array(
		    "isRunning" => "True", 
		);
		$output[]=$arr;
		$row=mysqli_fetch_assoc($result);
		$auctionId=$row['idAuction'];
		

		echo $auctionId;
	}

	print(json_encode($output));
	mysqli_close($con);
?>