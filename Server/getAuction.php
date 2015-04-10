<?php
	include 'config.php';
	$user_id=$_POST['id_user'];
	date_default_timezone_set("Asia/Kolkata");
	$time=date( 'Y-m-d H:i:s', time());
//	echo $time;
	$con=mysqli_connect($IP,$user,$pass,$db);
//	echo "select `idAuction`, `location`, `start_time`, `expctd_time`, `description` from `Auction` where `idUser`=\"$user_id\" and `end_time`>=\"$time\"";
	$result=mysqli_query($con,"select `idAuction`, `location`, `start_time`, `expctd_time`, `description` from `Auction` where `idUser`=\"$user_id\" and `end_time`>=\"$time\"") or die("Error: ".mysqli_error($con));
	
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
		while ($row=mysqli_fetch_assoc($result))
		{
			$output[]=$row;
			$auctionId=$row['idAuction'];
		}
//		echo $auctionId;
	}

	print(json_encode($output));
	mysqli_close($con);
?>