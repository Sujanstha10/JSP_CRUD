<!DOCTYPE html>
<html>
<head>
    <title>Update College</title>
</head>
<body>
    <h1>Update College</h1>
    <form action="UpdateCollege" method="post">
        <input type="hidden" name="_method" value="put">
        <label for="id">College ID:</label>
        <input type="text" id="id" name="id" required><br>
        <label for="name">Name:</label>
        <input type="text" id="name" name="name" required><br>
        <label for="department">Department:</label>
        <input type="text" id="department" name="department" required><br>
        <button type="submit">Update</button>
    </form>
</body>
</html>
