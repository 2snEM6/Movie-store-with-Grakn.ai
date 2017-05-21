#NOTES

##QRAQL QUERIES

### Find a relation by its components' IDs

match (is_downloaded_by: $x, downloaded_a: $y) isa download; 
$x has identifier "whatever"; $y has identifier "whatever"; offset 0; limit 100;