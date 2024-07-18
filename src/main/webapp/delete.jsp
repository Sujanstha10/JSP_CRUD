<!DOCTYPE html>
<html>
<head>
    <title>Delete College</title>
</head>
<body>
    <h1>Delete College</h1>
    <form action="DeleteCollege" method="post">
        <input type="hidden" name="_method" value="delete">
        <label for="id">College ID:</label>
        <input type="text" id="id" name="id" required><br>
        <button type="submit">Delete</button>
    </form>
</body>
</html>
