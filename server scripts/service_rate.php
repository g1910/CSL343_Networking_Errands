<?php
 


 
// include db connect class
require_once __DIR__ . '/db_connect.php';
 
// connecting to db
$db = new DB_CONNECT();

$response = array();

 $tag = $_POST['tag'];
 $counter = $_POST['counter'];
 $email = $_POST['email'];
     
$counter = (int)$counter;
	$counter=20*$counter;
	
 $res = mysql_query("SELECT idUser FROM User WHERE emailId='$email'")or die(mysql_error()) ;
 
	$id=mysql_fetch_array($res);
	$id=$id['idUser'];
	
 if ($tag=="1") {
   
	 

	  
// user was receiver
$result = mysql_query("SELECT idFeedback,location,start_time,end_time,name FROM (User join (Feedback  join Auction using (idAuction) ) on idUser=idSender) WHERE idReceiver='$id' and status=0  LIMIT 0 , $counter")or die(mysql_error()) ;

 }
 
 else if($tag=="2"){
     

	
	    
    //user was sender
$result = mysql_query("SELECT idFeedback,location,start_time,end_time,name FROM (User join (Feedback  join Auction using (idAuction) ) on idUser=idReceiver) WHERE idSender='$id' and status=0  LIMIT 0 , $counter")or die(mysql_error()) ;

	}
	
	// check for empty result

if (mysql_num_rows($result) > 0) {
    // looping through all results
    // requests node
    $response["Ratings"] = array();
 
    while ($row = mysql_fetch_array($result)) {
        // temp user array
           $Rating = array();
	    $Rating["idFeedback"] = $row["idFeedback"];
        $Rating["location"] = $row["location"];
        $Rating["start_time"] = $row["start_time"];
		$Rating["end_time"] = $row["end_time"];
		$Rating["name"] = $row["name"];
       
 
        // push single request into final response array
        array_push( $response["Ratings"], $Rating);
    }
    // success
    $response["success"] = 1;
 
    // echoing JSON response
    echo json_encode($response);
} 

else {
    // no requests found
    $response["success"] = 0;
    $response["message"] = "No reviews found";
 
    // echo no users JSON
    echo json_encode($response);
}
?>
