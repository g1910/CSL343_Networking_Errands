<?php
	include 'config.php';

	$con=mysqli_connect($IP,$user,$pass,$db);

	$id_auc = $_GET['id_auc'];
	$id_bid = $_GET['id_bid'];

	$result=mysqli_query($con,"delete from `Placed` where idBid=".$id_bid." and idAuction=".$id_auc) or die("Error: ".mysqli_error($con));

	$bid_request = array('tag' => 'bid_reject', );
	$output[] = $bid_request;

	if($result){
		$status = array('status' => true, );
	}else{
		$status = array('status' => false,);
	}

	$output[] = $status;

	echo json_encode($output);
?>