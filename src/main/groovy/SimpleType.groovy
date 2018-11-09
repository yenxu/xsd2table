class SimpleType extends Type {
    def enumerations = []
    def baseType
    def maxLength
    def pattern
    def minLength
    def minInclusive
    def maxInclusive
    def minExclusive
    def totalDigits

    def to_string() {
        "name:"+name+ " type:" +baseType+" maxLen:"+maxLength+" enumerations:"+ enumerations.toString() +" pattern:"+pattern
    }
    def parsing(it) {
        this.name = it.@name.text()
        this.baseType = it.restriction.@base.text()
        this.maxLength = it.maxLength.@value.text()
        this.pattern = it.restriction.pattern.@value.text()
        this.minLength = it.restriction.minLength.@value.text()
        this.maxLength = it.restriction.maxLength.@value.text()
        this.minInclusive = it.restriction.minInclusive.@value.text()
        this.maxInclusive = it.restriction.maxInclusive.@value.text()
        this.minExclusive = it.restriction.minExclusive.@value.text()
        this.totalDigits = it.restriction.totalDigits.@value.text()

        it.restriction.enumeration.each {
            this.enumerations << it.@value.text()
        }

        println("Simpletype:" + this.to_string())
        this
    }
}