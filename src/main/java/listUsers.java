public static void listUsers() {
    try{
        PreparedStatement ps = db.preparestatement("SELECT User_ID, Username, Password FROM Users");

        ResultSet results = ps.executeQuery();
        while (results.next()) {
            int User_ID = results.getInt(1);
            String Username = results.getString(3);
            System.out.println(User_ID + " " + Username + " " + Password);
        }
        } catch (Exception exception) {
            System.out.println("Database error: " + exception.getMessage());
        }
        }