<?php
 


 
// include db connect class
require_once __DIR__ . '/db_connect.php';
 
// connecting to db
$db = new DB_CONNECT();

$response = array();


 $id = $_POST['id'];
 $star = $_POST['star'];
 $review = $_POST['review'];

 $res = mysql_query("UPDATE Feedback SET stars='$star',description='$review',status=1 WHERE idFeedback='$id'")or die(mysql_error()) ;
 
 if (mysql_affected_rows() != 0) {

    echo "successfully updated";
}
else
{
"Review not submitted";
}


?>