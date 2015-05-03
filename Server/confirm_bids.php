<?php
	include 'config.php';

	$con=mysqli_connect($IP,$user,$pass,$db);

	$id_auc = $_GET['id_auc'];
	$id_bid = $_GET['id_bid'];
	$id_auc_user = $_GET['id_auc_user'];
	$id_bid_user = $_GET['id_bid_user'];

	$x = 0;
	foreach ($id_bid as $bid) {
		$query="update `Placed` set status='C' where idBid=".$bid." and idAuction=".$id_auc;
		// echo $query."<br>";
		$result=mysqli_query($con,$query) or die("Error:1 ".$x." ".mysqli_error($con));

		$query="delete from `Placed` where idBid=".$bid." and idAuction<>".$id_auc;
		$result=mysqli_query($con,$query) or die("Error:4 ".$x." ".mysqli_error($con));


        $query = "insert into Feedback(idSender,idReceiver,idAuction,status,role) values(".$id_auc_user.",".$id_bid_user[$x].",".$id_auc.",0,0)";
        // echo $query."<br>";
        $result=mysqli_query($con,$query) or die("Error:2 ".$x." ".mysqli_error($con));

        $query = "insert into Feedback(idSender,idReceiver,idAuction,status,role) values(".$id_bid_user[$x].",".$id_auc_user.",".$id_auc.",0,1)";
		// echo $query."<br>";
		$result=mysqli_query($con,$query) or die("Error:3 ".$x." ".mysqli_error($con));
		// echo $id_bid_user[$x];
		$x++;
	}

	$bid_request = array('tag' => 'bid_confirm', );
	$output[] = $bid_request;

	if($result){
		$status = array('status' => true, );
	}else{
		$status = array('status' => false,);
	}

	$output[] = $status;

	echo json_encode($output);
	mysqli_close($con);
?>	