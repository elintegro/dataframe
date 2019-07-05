/* Elintegro Dataframe is a framework designed to accelerate the process of full-stack application development. 
We invite you to join the community of developers making it even more powerful!
For more information please visit  https://www.elintegro.com

Copyright © 2007-2019  Elinegro Inc. Eugene Lipkovich, Shai Lipkovich

This program is under the terms of the GNU General Public License as published by the Free Software Foundation, version 3.
See the GNU General Public License for more details.
You should have received a copy of the GNU General Public License along with this program.  If not, see <http://www.gnu.org/licenses/>.
You are not required to accept this License, since you have not signed it. However, nothing else grants you permission to modify or distribute the Program or its derivative works. 
These actions are prohibited by law if you do not accept this License. Therefore, by modifying or distributing the Program or any work based on the Program, you indicate your acceptance of this License to do so, and all its terms and conditions for copying, distributing or modifying the Program or works based on it. */


package com.elintegro.erf.dataframe.service

import com.amazonaws.ClientConfiguration
import com.amazonaws.auth.BasicAWSCredentials
import com.amazonaws.services.s3.AmazonS3
import com.amazonaws.services.s3.AmazonS3Client
import com.amazonaws.services.s3.model.CannedAccessControlList
import com.amazonaws.services.s3.model.CreateBucketRequest
import com.amazonaws.services.s3.model.DeleteObjectRequest
import com.amazonaws.services.s3.model.PutObjectRequest
import grails.util.Holders

class AwsS3Service {

    def serviceMethod() {

    }

    public static AmazonS3 getAwsS3Client() {
        def access = Holders.grailsApplication.config.aws.s3
        BasicAWSCredentials awsCredentials = new BasicAWSCredentials(access.aws_access_key_id, access.aws_secret_access_key)
        ClientConfiguration config = new ClientConfiguration()
        config.setConnectionTimeout(120000)
        config.setSocketTimeout(120000)
        config.setMaxErrorRetry(20)
        AmazonS3 s3Client = new AmazonS3Client(awsCredentials, config)
        return s3Client
    }

    /**
     * The base url for s3 bucket is: https://elintegro.s3.amazonaws.com/
     */
    def uploadImage(def imageFile, def s3Path, String bucketName) {
        AmazonS3 s3Client = getAwsS3Client()
        boolean bucket = s3Client.doesBucketExist(bucketName)
        if(!bucket){
            s3Client.createBucket(new CreateBucketRequest(bucketName))
        }

        s3Client.putObject(
                new PutObjectRequest(bucketName, s3Path, (File) imageFile)
                        .withCannedAcl(CannedAccessControlList.PublicRead))
        return s3Client.getResourceUrl(bucketName, s3Path)

    }

    def deleteImageWithS3Url(def s3Url) {
        String[] tokens = s3Url.replace("https://", "").split("/", 2)
        String key = tokens[1]
        String bucketName = tokens[0].split("\\.", 2)[0]
        deleteImageWithFileName(bucketName,key)
    }

    def deleteImageWithFileName(String bucketName, String fileName){
        AmazonS3 s3Client = getAwsS3Client()
        boolean bucket = s3Client.doesBucketExist(bucketName)
        if(bucket) {
            s3Client.deleteObject(new DeleteObjectRequest(bucketName, fileName))
        }
    }

}