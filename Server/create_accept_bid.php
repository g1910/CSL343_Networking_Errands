<?php
	include 'config.php';

	$con=mysqli_connect($IP,$user,$pass,$db);

	$id_auc = $_GET['id_auc'];
	$id_bid = $_GET['id_bid'];
	$min_price = $_GET['min_price'];

	$bid_request = array('tag' => 'bid_request', );
	$output[] = $bid_request;

	mysqli_autocommit($con,FALSE);

	mysqli_query($con,"INSERT INTO `Auction_Categories`(`idAuction`,`minPrice`) VALUES (".$id_auc.",".$min_price.")") or die(mysqli_error($con));

	mysqli_query($con,"update `Placed` set status='A', idCategory=".mysqli_insert_id($con)." where idBid=".$id_bid." and idAuction=".$id_auc) or die("Error: ".mysqli_error($con));

	if(!mysqli_commit($con)){
		$status = array('status' => false, );
	}else{
		$status = array('status' => true,);
	}

	$output[] = $status;

	echo json_encode($output);
?>