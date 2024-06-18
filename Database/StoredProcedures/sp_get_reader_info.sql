USE LMSDB;
DROP PROCEDURE if exists getReaderInfo
GO  

CREATE PROCEDURE getReaderInfo 

	@readerid varchar(4)

AS   
BEGIN
Declare @Err_Msg AS varchar(50)
Declare @firstName As varchar(255)
Declare @lastName As varchar(255)
Declare @gender As char(1)
Declare @hkid As varchar(8)
Declare @dob As date
Declare @email As varchar(255)
Declare @address As varchar(255)
Declare @contactNo As char(8)
Declare @maxQuota As smallint
Declare @ac As varchar(10)



--check if id exist
IF (Select 1 from Reader where ReaderId=@readerid)=1
	BEGIN


		select @firstName=FirstName,@lastName=LastName,@gender=Gender,@hkid=HKID,@dob=DOB,@email=EmailAddress,@address=Address,@contactNo=ContactNo,@maxQuota=MaxQuota,@ac=Ac
		from Reader  
        where readerId=@readerId

	END

ELSE
	SET @Err_Msg='readerID not exist'


	IF (@Err_Msg is null)	
		SELECT 'SUCCESS' AS tranState,@readerid as ReaderId, @firstName as FirstName,@lastName as LastName,@gender as Gender,@hkid as HKID,@dob as DOB,@email as EmailAddress,@address as Address,@contactNo as ContactNo,@maxQuota as MaxQuota,@ac as Ac
	ELSE
		SELECT @Err_Msg AS tranState

	END
GO  
--execute getReaderInfo '0013';
--execute getReaderInfo '0113';
