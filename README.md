# SemanticTreeVisualizer
Visualizer for natural language semantic trees. Requires the Jackson Core, Annotations and Data-Bind libraries for XML parsing. Sample data is included.

Project produced for the University of Cape Town's 3rd year undergrad computer science course in collaboration with two other students. Personal contribution consisted of program front-end, back-end core and merging of other group member's contributions into the code base.

The program takes input in the form of a dmrs or xml file that denotes a collection of semantic trees. Loaded trees are available from the list on the left of the UI, and can be sent to the visualiser by double-clicking. Nodes in the visualiser can be shift-clicked to show adjacent nodes, control-clicked to show descendent nodes or clicked in pairs to show the shortest path between them (directed on a normal click, undirected on an alt-click).
