package com.basic;

public class HashCodeEqualImpl {
  int id;
  String name;
  long salary;
  boolean isstudent;
  float avgMark;
@Override
public int hashCode() {
	final int prime = 31;
	int result = 1;
	result = prime * result + Float.floatToIntBits(avgMark);
	result = prime * result + id;
	result = prime * result + (isstudent ? 1231 : 1237);
	result = prime * result + ((name == null) ? 0 : name.hashCode());
	result = prime * result + (int) (salary ^ (salary >>> 32));
	return result;
}
@Override
public boolean equals(Object obj) {
	if (this == obj)
		return true;
	if (obj == null)
		return false;
	if (getClass() != obj.getClass())
		return false;
	HashCodeEqualImpl other = (HashCodeEqualImpl) obj;
	if (Float.floatToIntBits(avgMark) != Float.floatToIntBits(other.avgMark))
		return false;
	if (id != other.id)
		return false;
	if (isstudent != other.isstudent)
		return false;
	if (name == null) {
		if (other.name != null)
			return false;
	} else if (!name.equals(other.name))
		return false;
	if (salary != other.salary)
		return false;
	return true;
}
  
  
}
