<?php
	include 'config.php';
	$user_id=$_GET['id_user'];
	date_default_timezone_set("Asia/Kolkata");
	$time=date( 'Y-m-d H:i:s', time());
//	echo $time;
	$con=mysqli_connect($IP,$user,$pass,$db);
//	echo "select `idAuction`, `location`, `start_time`, `expctd_time`, `description` from `Auction` where `idUser`=\"$user_id\" and `end_time`>=\"$time\"";
	$result=mysqli_query($con,"select * from `Auction` where `idUser`=\"$user_id\" and `start_time`<=\"$time\" and `expctd_time`>\"$time\"") or die("Error: ".mysqli_error($con));
	
	$num=mysqli_num_rows($result);

	$load = array(
		    "tag" => "loading", 
		);
	$output[]=$load;

	if ($num==0)
	{
		$arr = array(
		    "isRunning" => 0, 
		);
		$output[]=$arr;

	}
	else
	{
		
		$row=mysqli_fetch_assoc($result);
		if($row['end_time']<$time){
			$confirmedBids = mysqli_query($con,"select Bid.*,Price from (Auction natural join Placed) join Bid using(idBid) where status = 'C' and idAuction = ".$row['idAuction']) or die("Error: ".mysqli_error($con));
			if(mysqli_num_rows($confirmedBids)>0){
				$arr = array(
			    	"isRunning" => 3, 
				);
			}else{
				$arr = array(
			    	"isRunning" => 2, 
				);
			}
		}else{
			$arr = array(
			    "isRunning" => 1, 
			);
		}
		$output[]=$arr;
		$output[]=$row;
		if($output[1]['isRunning'] == 3){
			$cBidOutput = [];
			while ($cBids=mysqli_fetch_assoc($confirmedBids))
			{
			 	$orderBids = mysqli_query($con,"select * from `Order` where idBid=".$cBids['idBid']) or die("Error: ".mysqli_error($con));
			 	$orders = [];
			 	while($orderow = mysqli_fetch_assoc($orderBids)){
			 		$orders[] = $orderow;
			 	}
			 	$cBids['orders'] = $orders;
			 	$cBidOutput[] = $cBids;
			// 	$auctionId=$row['idAuction'];
			}
			$output[]=$cBidOutput;

		}else{
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

			$catCurrBidOutput = [];
			$catAuc = mysqli_query($con,"select * from Auction_Categories where idAuction=".$row['idAuction']) or die("Error: ".mysqli_error($con));
			while($cat = mysqli_fetch_assoc($catAuc)){
				$currBidOutput = [];

				$currBids = mysqli_query($con,"select Bid.*, Price from Placed natural join Bid where idCategory = ".$cat['idCategory']." order by Price") or die("Error: ".mysqli_error($con));

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

				$cat['bids'] = $currBidOutput;
				$catCurrBidOutput[] = $cat;

			}
			$output[]=$catCurrBidOutput;
		}
	}

	print(json_encode($output));
	mysqli_close($con);
?>