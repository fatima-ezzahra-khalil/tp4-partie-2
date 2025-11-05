<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="fr">
<head>
    <meta charset="UTF-8">
    <title>Liste des Livres</title>
    <style>
        body { font-family: Arial, sans-serif; background: #f5f5f5; padding: 20px; }
        table { width: 80%; margin: auto; border-collapse: collapse; background: #fff; }
        th, td { border: 1px solid #ccc; padding: 10px; text-align: center; }
        th { background-color: #0077cc; color: white; }
    </style>
</head>
<body>
<h2>Liste des Livres</h2>
<table>
    <tr>
        <th>Titre</th>
        <th>Auteur</th>
        <th>Prix</th>
        <th>Prix Réduit</th>
        <th>Éditeur</th>
    </tr>
    <c:forEach var="b" items="${books}">
        <tr>
            <td>${b.title}</td>
            <td>${b.author}</td>
            <td>${b.price}</td>
            <td>${b.discountedPrice}</td>
            <td>${b.publisher.name}</td>
        </tr>
    </c:forEach>
</table>
</body>
</html>
