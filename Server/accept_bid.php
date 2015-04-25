<?php
	include 'config.php';

	$con=mysqli_connect($IP,$user,$pass,$db);

	$id_auc = $_GET['id_auc'];
	$id_bid = $_GET['id_bid'];
	$id_cat = $_GET['id_cat'];

	$result=mysqli_query($con,"update `Placed` set status='A', idCategory=".$id_cat." where idBid=".$id_bid." and idAuction=".$id_auc) or die("Error: ".mysqli_error($con));

	$bid_request = array('tag' => 'bid_request', );
	$output[] = $bid_request;

	if($result){
		$status = array('status' => true, );
	}else{
		$status = array('status' => false,);
	}

	$output[] = $status;

	echo json_encode($output);
?>