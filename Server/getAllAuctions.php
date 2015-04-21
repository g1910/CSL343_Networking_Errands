<?php
	include 'config.php';
	include 'getRating.php';
	$user_id=$_GET['id_user'];
	date_default_timezone_set("Asia/Kolkata");
	$time=date( 'Y-m-d H:i:s', time());
//	echo $time;
	$con=mysqli_connect($IP,$user,$pass,$db);
//	echo "select `idAuction`, `location`, `start_time`, `expctd_time`, `description` from `Auction` where `idUser`=\"$user_id\" and `end_time`>=\"$time\"";
//	echo "select Auction.*,Temp.Price  from ( (( SELECT * FROM `Bid` Natural Join `Placed`) as Temp), `Auction`) where Temp.`idAuction` = Auction.`idAuction` and Temp.`idUser`=\"$user_id\" and `end_time`>=\"$time\"";
	$result=mysqli_query($con,"select Auction.*,Temp.Price  from ( (( SELECT * FROM `Bid` Natural Join `Placed`) as Temp), `Auction`) where Temp.`idAuction` = Auction.`idAuction` and Temp.`idUser`=\"$user_id\" and `end_time`>=\"$time\"") or die("Error: ".mysqli_error($con));
	
	$num=mysqli_num_rows($result);

	while ($row=mysqli_fetch_assoc($result))
	{
		$rating=getRating($row['idUser']);
	//	print json_encode($rating);
		$row['rating']=$rating[0]['rating'];
		$row['numRated']=$rating[0]['numRated'];
		$tout[]=$row;
	}
	$arr = array(
		    "Participating" => $tout, 
		);
	$output[]=$arr;

	$tout=[];
//	echo "select * from Auction where `idAuction` not in (select Auction.`idAuction` from ( (( SELECT * FROM `Bid` Natural Join `Placed`) as Temp), `Auction`) where Temp.`idAuction` = Auction.`idAuction` and Temp.`idUser`=\"$user_id\" and `end_time`>=\"$time\") and `end_time`>=\"$time\"";
	$result=mysqli_query($con,"select * from Auction where `idAuction` not in (select Auction.`idAuction` from ( (( SELECT * FROM `Bid` Natural Join `Placed`) as Temp), `Auction`) where Temp.`idAuction` = Auction.`idAuction` and Temp.`idUser`=\"$user_id\" and `end_time`>=\"$time\") and `end_time`>=\"$time\"") or die("Error: ".mysqli_error($con));
	
	$num=mysqli_num_rows($result);

	while ($row=mysqli_fetch_assoc($result))
	{
		$rating=getRating($row['idUser']);
	//	print json_encode($rating);
		$row['rating']=$rating[0]['rating'];
		$row['numRated']=$rating[0]['numRated'];
		$tout[]=$row;
	}
	$arr = array(
		    "Not_Participating" => $tout, 
		);
	$output[]=$arr;

//	echo "select Temp.* from ( (( SELECT * FROM `Bid` Natural Join `Placed`) as Temp), `Auction`) where Temp.`idAuction` = Auction.`idAuction` and Temp.`idUser`=\"$user_id\" and `end_time`>=\"$time\"";
	$result=mysqli_query($con,"select Temp.* from ( (( SELECT * FROM `Bid` Natural Join `Placed`) as Temp), `Auction`) where Temp.`idAuction` = Auction.`idAuction` and Temp.`idUser`=\"$user_id\" and `end_time`>=\"$time\"") or die("Error: ".mysqli_error($con));
	
	$num=mysqli_num_rows($result);
	$tout=[];
	while ($row=mysqli_fetch_assoc($result))
	{
		$orderBids = mysqli_query($con,"select * from `Order` where `idBid` = ".$row['idBid']) or die("Error: ".mysqli_error($con));
		$orders = [];
		while ($orderRow = mysqli_fetch_assoc($orderBids)){
			$orders[]=$orderRow;
		}
		$row['order']=$orders;

	//	echo "select idAuction from `Placed` where `idBid` = ".$row['idBid'];
		$placedBids = mysqli_query($con,"select idAuction from `Placed` where `idBid` = ".$row['idBid']) or die("Error: ".mysqli_error($con));
		$placed = [];
		while ($placedRow = mysqli_fetch_assoc($placedBids)){
			$placed[]=$placedRow;
		}
		$row['placed']=$placed;

		$tout[]=$row;
	}
	$arr = array(
		    "Bids" => $tout, 
		);
	$output[]=$arr;

//	echo "select * from `Order` where `idBid` in (select Temp.`idBid` from ( (( SELECT * FROM `Bid` Natural Join `Placed`) as Temp), `Auction`) where Temp.`idAuction` = Auction.`idAuction` and Temp.`idUser`=\"$user_id\" and `end_time`>=\"$time\")";
/*	$result=mysqli_query($con,"select * from `Order` where `idBid` in (select Temp.`idBid` from ( (( SELECT * FROM `Bid` Natural Join `Placed`) as Temp), `Auction`) where Temp.`idAuction` = Auction.`idAuction` and Temp.`idUser`=\"$user_id\" and `end_time`>=\"$time\")") or die("Error: ".mysqli_error($con));
	
	$num=mysqli_num_rows($result);
	$tout=[];
	while ($row=mysqli_fetch_assoc($result))
	{
		$tout[]=$row;
	}
	$arr = array(
		    "Order" => $tout, 
		);
	$output[]=$arr;
*/

//	print(json_encode($arr));
	print(json_encode($output));
	mysqli_close($con);
?>
