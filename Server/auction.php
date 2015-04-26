<?php
	include 'config.php';
	$location=$_GET['location'];
	$endtime=$_GET['endtime'];
	$expectedtime=$_GET['expected'];
	$description=$_GET['description'];
	$userid=$_GET['id_user'];
	$min_price = $_GET['min_price'];
	$con=mysqli_connect($IP,$user,$pass,$db);

	date_default_timezone_set("Asia/Kolkata");
	$starttime= date( 'Y-m-d H:i:s', time());

	mysqli_autocommit($con,FALSE);

	mysqli_query($con,"INSERT INTO `Auction`( `location`, `start_time`, `end_time`, `expctd_time`, `description`, `idUser`) VALUES (\"$location\",\"$starttime\",\"$endtime\",\"$expectedtime\",\"$description\",\"$userid\")")  or die(mysqli_error($con));

	mysqli_query($con,"INSERT INTO `Auction_Categories`(`idAuction`,`minPrice`) VALUES (".mysqli_insert_id($con).",".$_GET['min_price'].")") or die(mysqli_error($con));

	if(!mysqli_commit($con)){
		$status = array('status' => false, );
	}else{
		$status = array('status' => true, );
	}
	$output[]=$status;

	echo json_encode($output));
?>
