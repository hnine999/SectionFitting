package com.geometricmethods.intersectiondetection;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

import org.apache.commons.lang3.tuple.Pair;


public class Label
{
	//
	// THE ROTATIONAL DIRECTION OF A SPLIT OR JOIN.  MEASURED FROM THIS LABEL TO THE OTHER LABEL
	//
	public enum Wisedness
	{
		COUNTERCLOCKWISE(false),
		CLOCKWISE(true);

		private boolean value;

		Wisedness(boolean value)
		{
			this.value = value;
		}

		private Wisedness booleanToWisedness(boolean value)
		{
			return value ? CLOCKWISE : COUNTERCLOCKWISE;
		}

		public boolean getValue()
		{
			return value;
		}

		public Wisedness getOpposite() {
			return booleanToWisedness(!value);
		}
	}

	//
	// PAIR OF Label'S THAT CORRESPOND TO THE ScanLineNode'S STARTPAIR -- A PAIR OF ScanLineNode'S WHOSE STARTING LineSegment'S ARE JOINED AT
	// ONE VERTEX, AND THIS VERTEX IS TO THE LEFT OF THE OTHER TWO VERTEXES OF THE LineSegment'S.  THAT IS, THEY START FROM A LOCALLY LEFT-MOST
	// POINT ON A POLYGON.
	//
	public static class StartPair
	{
		private Label label1;
		private Label label2;

		private StartPair()
		{
			label1 = new Label();
			label2 = new Label();
			label1.setStartsWithLabel(label2 );
		}

		public Label getFirstLabel()
		{
			return label1;
		}

		public Label getSecondLabel()
		{
			return label2;
		}
	}

	//
	// USED TO MANAGE THE JOINS AND SPLITS OF THE LABELS ASSOCIATED WITH A GIVEN EVENT VERTEX.
	//
	// ITEMS IN THE counterClockwiseLabelPairSplitSet DATA MEMBER ARE PAIRS IF Label'S THAT ARE INCURRING A SPLIT,
	// AND THE ScanLineNode CORRESPONDING TO THE FIRST LABEL IS COUNTERCLOCKWISE FROM THE ScanLineNode CORRESPONDING TO THE SECOND LABEL.
	//
	// ITEMS IN THE clockwiseLabelPairJoinSet DATA MEMBER ARE PAIRS IF Label'S THAT ARE INCURRING A JOIN,
	// AND THE ScanLineNode CORRESPONDING TO THE FIRST LABEL IS CLOCKWISE FROM THE ScanLineNode CORRESPONDING TO THE SECOND LABEL.
	//
	public static class JoinSplitTable
	{
		private final Set<Pair<Label, Label>> counterClockwiseLabelPairSplitSet = new HashSet<>();
		private final Set<Pair<Label, Label>> clockwiseLabelPairJoinSet = new HashSet<>();

		//
		// TAKE JOINS OUT OF THE LABELS INVOLVED IN AN EVENT AND PLACE THEN IN THE JoinSplitTable
		//
		public JoinSplitTable(Set<Label> labelSet)
		{
			Set<Label> eventLabelSet = new HashSet<>(labelSet);

			while(!eventLabelSet.isEmpty())
			{
				Label primaryLabel = eventLabelSet.iterator().next();
				eventLabelSet.remove(primaryLabel);

				for(Label secondaryLabel : eventLabelSet)
				{
					Label.Wisedness wisedness = primaryLabel.removeJoin(secondaryLabel);
					if (wisedness != null)
					{
						addJoin(primaryLabel, secondaryLabel, wisedness);
					}
				}
			}

		}

		//
		// ADD A JOIN PAIR -- ALWAYS PAIRED IN CLOCKWISE ORDER
		//
		public void addJoin(Label label1, Label label2, Wisedness wisedness)
		{
			assert getJoin(label1, label2) == null;
			clockwiseLabelPairJoinSet.add(wisedness == Wisedness.CLOCKWISE ? Pair.of(label1, label2) : Pair.of(label2, label1));
		}

		//
		// REGISTER JOINS BETWEEN LABELS OF ScanLineNode'S THAT ARE CONVERGING INTO EVENT VERTEX
		//
		public void addJoins(List<Set<Label>> labelSetList)
		{
			Set<Label> afterLabelSet = new HashSet<>();
			for(Set<Label> labelSet : labelSetList)
			{
				afterLabelSet.addAll(labelSet);
			}

			for(Set<Label> currentLabelSet : labelSetList)
			{
				afterLabelSet.removeAll(currentLabelSet);
				for (Label afterLabel : afterLabelSet)
				{
					for(Label currentLabel : currentLabelSet)
					{
						addJoin(currentLabel, afterLabel, Wisedness.CLOCKWISE);
					}
				}
			}
		}

		//
		// TURN JOINS AFFILIATED WITH A DEGENERATE SPLIT INTO A SPLIT
		//
		public void processDegenerateSplits()
		{
			List<Pair<Label, Label>> removeJoinPairs = new ArrayList<>();

			for(Pair<Label, Label> pair : getJoins())
			{
				Label label1 = pair.getKey();
				Label label2 = pair.getValue();

				if (label1.hasDegenerateSplit(label2))
				{
					removeJoinPairs.add(pair);

					label1.removeDegenerateSplit(label2);
					addSplit(label1.getTerminatesWithLabel(), label2, Label.Wisedness.COUNTERCLOCKWISE);
				}
				else if (label2.hasDegenerateSplit(label1))
				{
					removeJoinPairs.add(pair);

					label2.removeDegenerateSplit(label1);
					addSplit(label1, label2.getTerminatesWithLabel(), Label.Wisedness.COUNTERCLOCKWISE);
				}
			}

			for(Pair<Label, Label> pair : removeJoinPairs)
			{
				removeJoinEntry(pair);
			}
		}

		//
		// GET THE WISEDNESS OF A JOIN
		//
		public Wisedness getJoin(Label label1, Label label2)
		{
			return clockwiseLabelPairJoinSet.contains(Pair.of(label1, label2)) ? Wisedness.CLOCKWISE :
			       clockwiseLabelPairJoinSet.contains(Pair.of(label2, label1)) ? Wisedness.COUNTERCLOCKWISE : null;
		}

		//
		// REMOVE A JOIN ENTRY
		//
		public void removeJoinEntry(Pair<Label, Label> pair)
		{
			removeJoinEntry(pair.getKey(), pair.getValue());
		}

		public void removeJoinEntry(Label label1, Label label2)
		{
			clockwiseLabelPairJoinSet.remove(Pair.of(label1, label2));
			clockwiseLabelPairJoinSet.remove(Pair.of(label2, label1));
		}

		//
		// GET ALL JOINS FROM THE JoinSplitTable
		//
		private Set<Pair<Label, Label>> getJoins()
		{
			return clockwiseLabelPairJoinSet;
		}

		//
		// ADD A SPLIT PAIR -- ALWAYS PAIRED IN COUNTERCLOCKWISE ORDER
		//
		public void addSplit(Label label1, Label label2, Wisedness wisedness)
		{
			assert getSplit(label1, label2) == null;
			counterClockwiseLabelPairSplitSet.add(wisedness == Wisedness.COUNTERCLOCKWISE ? Pair.of(label1, label2) : Pair.of(label2, label1));
		}

		//
		// REGISTER SPLITS BETWEEN LABELS OF ScanLineNode'S THAT ARE DIVERGING OUT OF AN EVENT VERTEX
		//
		public void addSplits(List<Set<Label>> labelSetList)
		{

			Set<Label> afterLabelSet = new HashSet<>();
			for(Set<Label> labelSet : labelSetList)
			{
				afterLabelSet.addAll(labelSet);
			}

			for(Set<Label> currentLabelSet : labelSetList)
			{
				afterLabelSet.removeAll(currentLabelSet);

				for (Label afterLabel : afterLabelSet)
				{
					for(Label currentLabel : currentLabelSet)
					{
						addSplit(currentLabel, afterLabel, Wisedness.COUNTERCLOCKWISE);
					}
				}
			}
		}

		//
		// TURN SPLITS AFFILIATED WITH A DENEGERATE JOIN INTO JOINS
		//
		public void processDegenerateJoins()
		{
			List<Pair<Label, Label>> removeSplitPairs = new ArrayList<>();

			for(Pair<Label, Label> pair : getSplits())
			{
				Label label1 = pair.getKey();
				Label label2 = pair.getValue();

				if (label1.hasDegenerateJoin(label2))
				{
					removeSplitPairs.add(pair);

					label1.removeDegenerateJoin(label2);
					addJoin(label1.getStartsWithLabel(), label2, Label.Wisedness.CLOCKWISE);
				}
				else if (label2.hasDegenerateJoin(label1))
				{
					removeSplitPairs.add(pair);

					label2.removeDegenerateJoin(label1);
					addJoin(label1, label2.getStartsWithLabel(), Label.Wisedness.CLOCKWISE);
				}
			}

			for(Pair<Label, Label> pair : removeSplitPairs)
			{
				removeSplitEntry(pair);
			}
		}

		//
		// GET THE WISEDNESS OF A SPLIT
		//
		public Wisedness getSplit(Label label1, Label label2)
		{
			return counterClockwiseLabelPairSplitSet.contains(Pair.of(label1, label2)) ? Wisedness.COUNTERCLOCKWISE :
			       counterClockwiseLabelPairSplitSet.contains(Pair.of(label2, label1)) ? Wisedness.CLOCKWISE : null;
		}

		//
		// REMOVE A SPLIT ENTRY
		//
		public void removeSplitEntry(Pair<Label, Label> pair)
		{
			removeSplitEntry(pair.getKey(), pair.getValue());
		}

		public void removeSplitEntry(Label label1, Label label2)
		{
			counterClockwiseLabelPairSplitSet.remove(Pair.of(label1, label2));
			counterClockwiseLabelPairSplitSet.remove(Pair.of(label2, label1));
		}

		//
		// GET ALL SPLITS FROM THE JoinSplitTable
		//
		private Set<Pair<Label, Label>> getSplits()
		{
			return counterClockwiseLabelPairSplitSet;
		}


		//
		// PLACE REMAINING JOINS AND SPLITS DIRECTLY ON THE LABELS FOR PROCESSING BY SUBSEQUENT EVENTS.
		//
		public boolean addJoinsAndSplits()
		{
			for(Pair<Label, Label> pair : getJoins())
			{
				pair.getKey().addJoin(pair.getValue(), Label.Wisedness.CLOCKWISE);
			}

			for(Pair<Label, Label> pair : getSplits())
			{
				// DON'T PUT SPLIT BETWEEN MEMBERS OF START PAIR UNLESS THERE IS A JOIN
				if (pair.getKey().addSplit(pair.getValue(), Label.Wisedness.COUNTERCLOCKWISE))
				{
					return true;
				}
			}

			return false;
		}

	}

	private static int uniqueIdentifier = 0;

	// HOW LABELS ARE COMPARED
	private static int getUniqueIdentifier()
	{
		return ++uniqueIdentifier;
	}


	// HOW TO GET A NEW START PAIR
	public static StartPair getLabelStartPair()
	{
		return new StartPair();
	}

	// SET UP TERMINAL PAIR OF LABELS
	public static void terminatesWith(Label label1, Label label2)
	{
		label1.setTerminatesWith(label2);
	}

	//
	// THIS METHOD "RETIRES" A TERMINAL PAIR, INASMUCH AS IT
	// - TRANSFERS ANY JOINS ON ONE MEMBER OF THE TERMINAL PAIR TO SPLITS ON THE OTHER
	// - MAKES THE startWithLabel'S OF THEIR RESPECTIVE startWithLabel'S THE startWithLabel'S
	//   OF ONE ANOTHER.
	//
	public static boolean retireTerminalPair(Pair<Label, Label> labelPair)
	{
		Label first = labelPair.getKey();
		Label second = labelPair.getValue();

		{
			// PUT INTO LIST TO AVOID ConcurrentModificationException
			List<Map.Entry<Label, Label.Wisedness>> entryList = new ArrayList<>(first.getJoins().entrySet());
			for (Map.Entry<Label, Label.Wisedness> entry : entryList)
			{
				Label entryLabel = entry.getKey();
				first.removeJoin(entryLabel);
				if (second.addSplit(entryLabel.getTerminatesWithLabel(), entry.getValue()))
				{
					return true;
				}
			}
		}
		{
			// PUT INTO LIST TO AVOID ConcurrentModificationException
			List<Map.Entry<Label, Label.Wisedness>> entryList = new ArrayList<>(second.getJoins().entrySet());
			for (Map.Entry<Label, Label.Wisedness> entry : entryList)
			{
				Label entryLabel = entry.getKey();
				second.removeJoin(entryLabel);
				if (first.addSplit(entryLabel.getTerminatesWithLabel(), entry.getValue()))
				{
					return true;
				}
			}
		}

		first.startsWithLabel.setStartsWithLabel(second.startsWithLabel);
		second.startsWithLabel.setStartsWithLabel(first.startsWithLabel);

		return false;
	}


	private int identifier = getUniqueIdentifier();


	// MAP THAT KEEPS TRACK OF THE OTHER LABELS WITH WHICH THIS LABEL HAS A JOIN, AND THE WISEDNESS OF THIS JOIN.
	private Map<Label, Wisedness> joins = new HashMap<>();

	// THIS IS SHARED WITH THE LABEL THAT "terminatesWith" THIS LABEL, AND KEEPS TRACK
	// OF WHICH LABELS HAVE A DEGENERATE JOIN WITH THIS LABEL AND ITS "startsWith" LABEL.
	private Set<Label> degenerateJoinSet = new HashSet<>();

	//
	// THIS IS SHARED WITH THE LABEL THAT "terminatesWith" THIS LABEL.  IT KEEPS TRACK OF
	// WHICH LABELS HAVE A DEGENERATE SPLIT WITH THIS LABEL AND ITS "terminatesWith" LABEL.
	//
	private Set<Label> degenerateSplitSet = new HashSet<>();

	// LABEL OF ScanLineNode WITH WHICH THE ScanLineNode THAT HAS THIS LABEL HAS A COMMON (IDENTICAL) STARTING VERTEX.
	private Label startsWithLabel;

	// LABEL OF ScanLineNode WITH WHICH THE ScanLineNode THAT HAS THIS LABEL HAS A COMMON (IDENTICAL) TERMINAL VERTEX.
	private Label terminatesWithLabel;


	private Label() { }


	// FOR A START PAIR OF LABELS, SETS THEIR startsWithLabel
	private void setStartsWithLabel(Label otherLabel)
	{
		startsWithLabel = otherLabel;
		otherLabel.startsWithLabel = this;

		// SET THE DEGENERATE JOIN SET TO BE THE SAME FOR BOTH START LABELS
		Set<Label> degenerateJoinSet = new HashSet<>();
		setDegenerateJoinSet(degenerateJoinSet);
		startsWithLabel.setDegenerateJoinSet(degenerateJoinSet);
	}

	// RETRIEVE THE LABEL THIS LABEL STARTS WITH
	Label getStartsWithLabel() {
		return startsWithLabel;
	}

	// FOR A TERMINAL PAIR OF LABELS, SET THEIR terminatesWithLabel
	private void setTerminatesWith(Label otherLabel)
	{
		terminatesWithLabel = otherLabel;
		otherLabel.terminatesWithLabel = this;

		// SET THE DEGENERATE SPLIT SET AND PERMANENT DEGENERATE SPLIT SET TO BE THE SAME FOR BOTH TERMINAL LABELS
		Set<Label> degenerateSplitSet = new HashSet<>();
		setDegenerateSplitSet(degenerateSplitSet);
		otherLabel.setDegenerateSplitSet(degenerateSplitSet);

	}

	// RETRIEVE THE LABEL THIS LABEL TERMINATES WITH
	private Label getTerminatesWithLabel()
	{
		return terminatesWithLabel;
	}


	public void addJoin(Label otherLabel, Wisedness wisedness)
	{
		if (!otherLabel.equals(getTerminatesWithLabel()))
		{
			joins.put(otherLabel, wisedness);
			otherLabel.joins.put(this, wisedness.getOpposite());
		}
	}

	// REMOVE A JOIN WITH A GIVEN LABEL AND RETURN ITS WISEDNESS
	public Wisedness removeJoin(Label otherLabel)
	{
		otherLabel.joins.remove(this);
		return joins.remove(otherLabel);
	}

	// QUERY THE WISEDNESS OF A JOIN
	public Wisedness getJoin(Label otherLabel)
	{
		return joins.getOrDefault(otherLabel, null);
	}

	// CHECK FOR A JOIN BETWEEN THIS AND ANOTHER LABEL
	private boolean hasJoin(Label otherLabel)
	{
		return joins.containsKey(otherLabel);
	}

	// RETURN ALL OF THE JOINS OF THIS LABEL
	Map<Label, Wisedness> getJoins()
	{
		return joins;
	}


	public boolean addSplit(Label otherLabel, Wisedness splitWisedness)
	{
		// A LABEL CAN HAVE A JOIN/SPLIT RELATIONSHIP WITH ITS startsWithLabel, BUT ONLY AFTER THE FIRST TIME
		// THEIR RESPECTIVE ScanLineNode'S SPLIT.  THIS IS USUALLY AT THEIR ScanLineNode'S MUTUAL START VERTEX,
		// BUT CAN BE LATER IF THE ScanLineNode'S START OUT IN THE SAME ScanLineGroup.
		// YOU CAN DETECT THAT THE SPLIT BETWEEN A LABEL AND ITS startsWithLabel IS NOT THE FIRST IF THERE IS ALREADY
		// A JOIN BETWEEN BETWEEN THEM.
		if (otherLabel.equals(getStartsWithLabel()) && !hasJoin(otherLabel))
		{
			return false;
		}

		// IF THIS SPLIT IS PAIRED WITH A JOIN, CHECK FOR AN EXTENDED INTERSECTION
		if (hasJoin(otherLabel))
		{
			return removeJoin(otherLabel) == splitWisedness;
		}

		// OTHERWISE, REFLECT THE SPLIT AS A JOIN ON THE RESPECTIVE startsWithLabel'S
		startsWithLabel.addJoin(otherLabel.startsWithLabel, splitWisedness);
		return false;
	}

	// SETS THE DEGENERATE JOIN SET OF THIS LABEL.  THIS SHOULD BE DONE IN CONJUNCTION WITH SETTING
	// THE DEGENERATE JOIN SET OF THE startsWithLAbel TO THIS SAME SET.
	private void setDegenerateJoinSet(Set<Label> degenerateJoinSet)
	{
		this.degenerateJoinSet = degenerateJoinSet;
	}

	// ADD TO THE DEGENERATE JOIN SET OF THIS LABEL AND ITS startsWith LABEL.
	public void addDegenerateJoin(Label label)
	{
		degenerateJoinSet.add(label);
	}

	// REMOVE FROM THE DEGENERATE JOIN SET OF THIS LABEL AND ITS startsWith LABEL.
	private void removeDegenerateJoin(Label label)
	{
		degenerateJoinSet.remove(label);
	}

	// CHECK FOR DEGENERATE JOIN
	private boolean hasDegenerateJoin(Label label)
	{
		return degenerateJoinSet.contains(label);
	}


	// SETS THE DEGENERATE SPLIT SET OF THIS LABEL.  THIS SHOULD BE DONE IN CONJUNCTION WITH SETTING
	// THE DEGENERATE SPLIT SET OF THE terminatesWithLabel TO THIS SAME SET.
	private void setDegenerateSplitSet(Set<Label> degenerateSplitSet)
	{
		this.degenerateSplitSet = degenerateSplitSet;
	}

	// ADD DEGENERATE SPLIT
	public void addDegenerateSplit(Label label)
	{
		degenerateSplitSet.add(label);
	}

	// GET THE WHOLE DEGENERATE SPLIT SET
	Set<Label> getDegenerateSplitSet()
	{
		return degenerateSplitSet;
	}

	// REMOVE FROM THE DEGENERATE SPLIT SET
	private void removeDegenerateSplit(Label label)
	{
		degenerateSplitSet.remove(label);
	}

	// CHECK FOR DEGENERATE SPLIT
	private boolean hasDegenerateSplit(Label label)
	{
		return degenerateSplitSet.contains(label);
	}

	@Override
	public boolean equals(Object other)
	{
		return other != null && other.getClass() == Label.class && identifier == ((Label)other).identifier;
	}

	@Override
	public int hashCode()
	{
		return Objects.hash(identifier);
	}
}
