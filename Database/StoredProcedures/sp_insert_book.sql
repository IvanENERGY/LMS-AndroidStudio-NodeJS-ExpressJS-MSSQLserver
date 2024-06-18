USE LMSDB;
DROP PROCEDURE if exists InsertBook 
GO  

CREATE PROCEDURE InsertBook  
    @title varchar(255),   
    @author varchar(255) ,
	@publisher varchar(255),
	@isbn char(13),
	@publishingYear char(4),
	@ac varchar(10) 
AS   
BEGIN
Declare @Err_Msg AS varchar(50)
Declare @EditStaffID As char(4)
SET @EditStaffID=(Select StaffId from Staff where Ac=@ac)
--check if isbn already exist
IF (Select 1 from book where ISBN=@isbn)=1
	SET @Err_Msg='ISBN already exist'
ELSE
	BEGIN
	--perform insert
		BEGIN TRY
			BEGIN TRAN

			Insert into Book(Title,Author,Publisher,ISBN,PublishingYear,EditStaffID) 
			values(@title,@author,@publisher,@isbn,@publishingYear,@EditStaffID)

			COMMIT TRAN
		END TRY
		BEGIN CATCH
			SET @Err_Msg=ERROR_MESSAGE()
			ROLLBACK TRAN
		END CATCH	
	END

	IF (@Err_Msg is null)	
		SELECT 'SUCCESS' AS tranState
	ELSE
		SELECT @Err_Msg AS tranState

	END
GO  
--execute InsertBook "TestBookTitle","TestAuthor","TestPublisher","1234567890999",2019,"jack4" ;