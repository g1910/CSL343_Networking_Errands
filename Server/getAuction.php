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
		$row=mysqli_fetch_assoc($result);
		$output[]=$row;
		$pBidOutput = [];
		// echo $row['idAuction'];
		$pendingBids = mysqli_query($con,"select Bid.* from (Auction natural join Placed) join Bid using(idBid) where status = 'P' and idAuction = ".$row['idAuction']) or die("Error: ".mysqli_error($con));
		while ($pBids=mysqli_fetch_assoc($pendingBids))
		{
		 	$orderBids = mysqli_query($con,"select * from `Order` where idBid=".$pBids['idBid']) or die("Error: ".mysqli_error($con));
		 	$orders = [];
		 	while($orderow = mysqli_fetch_assoc($orderBids)){
		 		$orders[] = $orderow;
		 	}
		 	$pBids['orders'] = $orders;
		 	$pBidOutput[] = $pBids;
		// 	$auctionId=$row['idAuction'];
		}
		$output[]=$pBidOutput;

		$currBidOutput = [];
		// echo $row['idAuction'];
		$currBids = mysqli_query($con,"select Bid.* from (Auction natural join Placed) join Bid using(idBid) where status <> 'P' and idAuction = ".$row['idAuction']) or die("Error: ".mysqli_error($con));
		while ($pBids=mysqli_fetch_assoc($currBids))
		{
		 	// print(json_encode($pBids));
		 	$orderBids = mysqli_query($con,"select * from `Order` where idBid=".$pBids['idBid']) or die("Error: ".mysqli_error($con));
		 	$orders = [];
		 	while($orderow = mysqli_fetch_assoc($orderBids)){
		 		// print(json_encode($orderow));
		 		$orders[] = $orderow;
		 	}
		 	$pBids['orders'] = $orders;
		 	$currBidOutput[] = $pBids;
		// 	$auctionId=$row['idAuction'];
		}
		$output[]=$currBidOutput;
		// print(json_encode($pBidOutput));
	}

	print(json_encode($output));
	mysqli_close($con);
?>