package clashsoft.clashsoftapi.util;

public class CSVar<T>
{
	public final String name;
	public T value;
	public T prevValue;
	
	public CSVar(String name, T value)
	{
		this.name = name;
		this.value = value;
	}
	
	public void set(T value)
	{
		this.prevValue = this.value;
		this.value = value;
	}
	
	public void unset()
	{
		this.value = this.prevValue;
		this.prevValue = null;
	}
	
	public T get()
	{
		return value;
	}
	
	public T getLast()
	{
		return prevValue;
	}

	@Override
	public int hashCode()
	{
		final int prime = 31;
		int result = 1;
		result = prime * result + ((this.prevValue == null) ? 0 : this.prevValue.hashCode());
		result = prime * result + ((this.name == null) ? 0 : this.name.hashCode());
		result = prime * result + ((this.value == null) ? 0 : this.value.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj)
	{
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		CSVar other = (CSVar) obj;
		if (this.prevValue == null)
		{
			if (other.prevValue != null)
				return false;
		}
		else if (!this.prevValue.equals(other.prevValue))
			return false;
		if (this.name == null)
		{
			if (other.name != null)
				return false;
		}
		else if (!this.name.equals(other.name))
			return false;
		if (this.value == null)
		{
			if (other.value != null)
				return false;
		}
		else if (!this.value.equals(other.value))
			return false;
		return true;
	}

	@Override
	public String toString()
	{
		return "CSVar [name=" + this.name + ", value=" + this.value + ", prevValue=" + this.prevValue + "]";
	}
}
