<?php
	include 'config.php';
	$user_id=$_POST['id_user'];
	$time=date( 'Y-m-d H:i:s', time());
	$con=mysqli_connect($IP,$user,$pass,$db);
	$result=mysqli_query($con,"select `idAuction`, `location`, `start_time`, `expctd_time`, `description` from `Auction` where `User_idUser`=\"$user_id\" and `end_time`>=\"$time\"") or die("Error: ".mysqli_error($con));
	$arr = array(
	    "isRunning" => "False", 
	);
	$output[]=$arr;
	while ($row=mysqli_fetch_assoc($result))
		$output[]=$row;
	print(json_encode($output));
	mysqli_close($con);
?>