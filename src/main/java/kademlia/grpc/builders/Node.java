// Generated by the protocol buffer compiler.  DO NOT EDIT!
// source: Kademlia.proto

package kademlia.grpc.builders;

/**
 * Protobuf type {@code kademlia.grpc.builders.Node}
 */
public  final class Node extends
    com.google.protobuf.GeneratedMessageV3 implements
    // @@protoc_insertion_point(message_implements:kademlia.grpc.builders.Node)
    NodeOrBuilder {
  // Use Node.newBuilder() to construct.
  private Node(com.google.protobuf.GeneratedMessageV3.Builder<?> builder) {
    super(builder);
  }
  private Node() {
    id_ = com.google.protobuf.ByteString.EMPTY;
    address_ = "";
    pubKey_ = "";
  }

  @Override
  public final com.google.protobuf.UnknownFieldSet
  getUnknownFields() {
    return com.google.protobuf.UnknownFieldSet.getDefaultInstance();
  }
  private Node(
      com.google.protobuf.CodedInputStream input,
      com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws com.google.protobuf.InvalidProtocolBufferException {
    this();
    int mutable_bitField0_ = 0;
    try {
      boolean done = false;
      while (!done) {
        int tag = input.readTag();
        switch (tag) {
          case 0:
            done = true;
            break;
          default: {
            if (!input.skipField(tag)) {
              done = true;
            }
            break;
          }
          case 10: {

            id_ = input.readBytes();
            break;
          }
          case 18: {
            String s = input.readStringRequireUtf8();

            address_ = s;
            break;
          }
          case 26: {
            String s = input.readStringRequireUtf8();

            pubKey_ = s;
            break;
          }
        }
      }
    } catch (com.google.protobuf.InvalidProtocolBufferException e) {
      throw e.setUnfinishedMessage(this);
    } catch (java.io.IOException e) {
      throw new com.google.protobuf.InvalidProtocolBufferException(
          e).setUnfinishedMessage(this);
    } finally {
      makeExtensionsImmutable();
    }
  }
  public static final com.google.protobuf.Descriptors.Descriptor
      getDescriptor() {
    return Kademlia.internal_static_kademlia_grpc_builders_Node_descriptor;
  }

  protected FieldAccessorTable
      internalGetFieldAccessorTable() {
    return Kademlia.internal_static_kademlia_grpc_builders_Node_fieldAccessorTable
        .ensureFieldAccessorsInitialized(
            Node.class, Builder.class);
  }

  public static final int ID_FIELD_NUMBER = 1;
  private com.google.protobuf.ByteString id_;
  /**
   * <code>bytes id = 1;</code>
   */
  public com.google.protobuf.ByteString getId() {
    return id_;
  }

  public static final int ADDRESS_FIELD_NUMBER = 2;
  private volatile Object address_;
  /**
   * <code>string address = 2;</code>
   */
  public String getAddress() {
    Object ref = address_;
    if (ref instanceof String) {
      return (String) ref;
    } else {
      com.google.protobuf.ByteString bs = 
          (com.google.protobuf.ByteString) ref;
      String s = bs.toStringUtf8();
      address_ = s;
      return s;
    }
  }
  /**
   * <code>string address = 2;</code>
   */
  public com.google.protobuf.ByteString
      getAddressBytes() {
    Object ref = address_;
    if (ref instanceof String) {
      com.google.protobuf.ByteString b = 
          com.google.protobuf.ByteString.copyFromUtf8(
              (String) ref);
      address_ = b;
      return b;
    } else {
      return (com.google.protobuf.ByteString) ref;
    }
  }

  public static final int PUBKEY_FIELD_NUMBER = 3;
  private volatile Object pubKey_;
  /**
   * <code>string pubKey = 3;</code>
   */
  public String getPubKey() {
    Object ref = pubKey_;
    if (ref instanceof String) {
      return (String) ref;
    } else {
      com.google.protobuf.ByteString bs = 
          (com.google.protobuf.ByteString) ref;
      String s = bs.toStringUtf8();
      pubKey_ = s;
      return s;
    }
  }
  /**
   * <code>string pubKey = 3;</code>
   */
  public com.google.protobuf.ByteString
      getPubKeyBytes() {
    Object ref = pubKey_;
    if (ref instanceof String) {
      com.google.protobuf.ByteString b = 
          com.google.protobuf.ByteString.copyFromUtf8(
              (String) ref);
      pubKey_ = b;
      return b;
    } else {
      return (com.google.protobuf.ByteString) ref;
    }
  }

  private byte memoizedIsInitialized = -1;
  public final boolean isInitialized() {
    byte isInitialized = memoizedIsInitialized;
    if (isInitialized == 1) return true;
    if (isInitialized == 0) return false;

    memoizedIsInitialized = 1;
    return true;
  }

  public void writeTo(com.google.protobuf.CodedOutputStream output)
                      throws java.io.IOException {
    if (!id_.isEmpty()) {
      output.writeBytes(1, id_);
    }
    if (!getAddressBytes().isEmpty()) {
      com.google.protobuf.GeneratedMessageV3.writeString(output, 2, address_);
    }
    if (!getPubKeyBytes().isEmpty()) {
      com.google.protobuf.GeneratedMessageV3.writeString(output, 3, pubKey_);
    }
  }

  public int getSerializedSize() {
    int size = memoizedSize;
    if (size != -1) return size;

    size = 0;
    if (!id_.isEmpty()) {
      size += com.google.protobuf.CodedOutputStream
        .computeBytesSize(1, id_);
    }
    if (!getAddressBytes().isEmpty()) {
      size += com.google.protobuf.GeneratedMessageV3.computeStringSize(2, address_);
    }
    if (!getPubKeyBytes().isEmpty()) {
      size += com.google.protobuf.GeneratedMessageV3.computeStringSize(3, pubKey_);
    }
    memoizedSize = size;
    return size;
  }

  private static final long serialVersionUID = 0L;
  @Override
  public boolean equals(final Object obj) {
    if (obj == this) {
     return true;
    }
    if (!(obj instanceof Node)) {
      return super.equals(obj);
    }
    Node other = (Node) obj;

    boolean result = true;
    result = result && getId()
        .equals(other.getId());
    result = result && getAddress()
        .equals(other.getAddress());
    result = result && getPubKey()
        .equals(other.getPubKey());
    return result;
  }

  @Override
  public int hashCode() {
    if (memoizedHashCode != 0) {
      return memoizedHashCode;
    }
    int hash = 41;
    hash = (19 * hash) + getDescriptor().hashCode();
    hash = (37 * hash) + ID_FIELD_NUMBER;
    hash = (53 * hash) + getId().hashCode();
    hash = (37 * hash) + ADDRESS_FIELD_NUMBER;
    hash = (53 * hash) + getAddress().hashCode();
    hash = (37 * hash) + PUBKEY_FIELD_NUMBER;
    hash = (53 * hash) + getPubKey().hashCode();
    hash = (29 * hash) + unknownFields.hashCode();
    memoizedHashCode = hash;
    return hash;
  }

  public static Node parseFrom(
      java.nio.ByteBuffer data)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data);
  }
  public static Node parseFrom(
      java.nio.ByteBuffer data,
      com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data, extensionRegistry);
  }
  public static Node parseFrom(
      com.google.protobuf.ByteString data)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data);
  }
  public static Node parseFrom(
      com.google.protobuf.ByteString data,
      com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data, extensionRegistry);
  }
  public static Node parseFrom(byte[] data)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data);
  }
  public static Node parseFrom(
      byte[] data,
      com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data, extensionRegistry);
  }
  public static Node parseFrom(java.io.InputStream input)
      throws java.io.IOException {
    return com.google.protobuf.GeneratedMessageV3
        .parseWithIOException(PARSER, input);
  }
  public static Node parseFrom(
      java.io.InputStream input,
      com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws java.io.IOException {
    return com.google.protobuf.GeneratedMessageV3
        .parseWithIOException(PARSER, input, extensionRegistry);
  }
  public static Node parseDelimitedFrom(java.io.InputStream input)
      throws java.io.IOException {
    return com.google.protobuf.GeneratedMessageV3
        .parseDelimitedWithIOException(PARSER, input);
  }
  public static Node parseDelimitedFrom(
      java.io.InputStream input,
      com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws java.io.IOException {
    return com.google.protobuf.GeneratedMessageV3
        .parseDelimitedWithIOException(PARSER, input, extensionRegistry);
  }
  public static Node parseFrom(
      com.google.protobuf.CodedInputStream input)
      throws java.io.IOException {
    return com.google.protobuf.GeneratedMessageV3
        .parseWithIOException(PARSER, input);
  }
  public static Node parseFrom(
      com.google.protobuf.CodedInputStream input,
      com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws java.io.IOException {
    return com.google.protobuf.GeneratedMessageV3
        .parseWithIOException(PARSER, input, extensionRegistry);
  }

  public Builder newBuilderForType() { return newBuilder(); }
  public static Builder newBuilder() {
    return DEFAULT_INSTANCE.toBuilder();
  }
  public static Builder newBuilder(Node prototype) {
    return DEFAULT_INSTANCE.toBuilder().mergeFrom(prototype);
  }
  public Builder toBuilder() {
    return this == DEFAULT_INSTANCE
        ? new Builder() : new Builder().mergeFrom(this);
  }

  @Override
  protected Builder newBuilderForType(
      BuilderParent parent) {
    Builder builder = new Builder(parent);
    return builder;
  }
  /**
   * Protobuf type {@code kademlia.grpc.builders.Node}
   */
  public static final class Builder extends
      com.google.protobuf.GeneratedMessageV3.Builder<Builder> implements
      // @@protoc_insertion_point(builder_implements:kademlia.grpc.builders.Node)
      NodeOrBuilder {
    public static final com.google.protobuf.Descriptors.Descriptor
        getDescriptor() {
      return Kademlia.internal_static_kademlia_grpc_builders_Node_descriptor;
    }

    protected FieldAccessorTable
        internalGetFieldAccessorTable() {
      return Kademlia.internal_static_kademlia_grpc_builders_Node_fieldAccessorTable
          .ensureFieldAccessorsInitialized(
              Node.class, Builder.class);
    }

    // Construct using kademlia.grpc.builders.Node.newBuilder()
    private Builder() {
      maybeForceBuilderInitialization();
    }

    private Builder(
        BuilderParent parent) {
      super(parent);
      maybeForceBuilderInitialization();
    }
    private void maybeForceBuilderInitialization() {
      if (com.google.protobuf.GeneratedMessageV3
              .alwaysUseFieldBuilders) {
      }
    }
    public Builder clear() {
      super.clear();
      id_ = com.google.protobuf.ByteString.EMPTY;

      address_ = "";

      pubKey_ = "";

      return this;
    }

    public com.google.protobuf.Descriptors.Descriptor
        getDescriptorForType() {
      return Kademlia.internal_static_kademlia_grpc_builders_Node_descriptor;
    }

    public Node getDefaultInstanceForType() {
      return Node.getDefaultInstance();
    }

    public Node build() {
      Node result = buildPartial();
      if (!result.isInitialized()) {
        throw newUninitializedMessageException(result);
      }
      return result;
    }

    public Node buildPartial() {
      Node result = new Node(this);
      result.id_ = id_;
      result.address_ = address_;
      result.pubKey_ = pubKey_;
      onBuilt();
      return result;
    }

    public Builder clone() {
      return (Builder) super.clone();
    }
    public Builder setField(
        com.google.protobuf.Descriptors.FieldDescriptor field,
        Object value) {
      return (Builder) super.setField(field, value);
    }
    public Builder clearField(
        com.google.protobuf.Descriptors.FieldDescriptor field) {
      return (Builder) super.clearField(field);
    }
    public Builder clearOneof(
        com.google.protobuf.Descriptors.OneofDescriptor oneof) {
      return (Builder) super.clearOneof(oneof);
    }
    public Builder setRepeatedField(
        com.google.protobuf.Descriptors.FieldDescriptor field,
        int index, Object value) {
      return (Builder) super.setRepeatedField(field, index, value);
    }
    public Builder addRepeatedField(
        com.google.protobuf.Descriptors.FieldDescriptor field,
        Object value) {
      return (Builder) super.addRepeatedField(field, value);
    }
    public Builder mergeFrom(com.google.protobuf.Message other) {
      if (other instanceof Node) {
        return mergeFrom((Node)other);
      } else {
        super.mergeFrom(other);
        return this;
      }
    }

    public Builder mergeFrom(Node other) {
      if (other == Node.getDefaultInstance()) return this;
      if (other.getId() != com.google.protobuf.ByteString.EMPTY) {
        setId(other.getId());
      }
      if (!other.getAddress().isEmpty()) {
        address_ = other.address_;
        onChanged();
      }
      if (!other.getPubKey().isEmpty()) {
        pubKey_ = other.pubKey_;
        onChanged();
      }
      onChanged();
      return this;
    }

    public final boolean isInitialized() {
      return true;
    }

    public Builder mergeFrom(
        com.google.protobuf.CodedInputStream input,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws java.io.IOException {
      Node parsedMessage = null;
      try {
        parsedMessage = PARSER.parsePartialFrom(input, extensionRegistry);
      } catch (com.google.protobuf.InvalidProtocolBufferException e) {
        parsedMessage = (Node) e.getUnfinishedMessage();
        throw e.unwrapIOException();
      } finally {
        if (parsedMessage != null) {
          mergeFrom(parsedMessage);
        }
      }
      return this;
    }

    private com.google.protobuf.ByteString id_ = com.google.protobuf.ByteString.EMPTY;
    /**
     * <code>bytes id = 1;</code>
     */
    public com.google.protobuf.ByteString getId() {
      return id_;
    }
    /**
     * <code>bytes id = 1;</code>
     */
    public Builder setId(com.google.protobuf.ByteString value) {
      if (value == null) {
    throw new NullPointerException();
  }
  
      id_ = value;
      onChanged();
      return this;
    }
    /**
     * <code>bytes id = 1;</code>
     */
    public Builder clearId() {
      
      id_ = getDefaultInstance().getId();
      onChanged();
      return this;
    }

    private Object address_ = "";
    /**
     * <code>string address = 2;</code>
     */
    public String getAddress() {
      Object ref = address_;
      if (!(ref instanceof String)) {
        com.google.protobuf.ByteString bs =
            (com.google.protobuf.ByteString) ref;
        String s = bs.toStringUtf8();
        address_ = s;
        return s;
      } else {
        return (String) ref;
      }
    }
    /**
     * <code>string address = 2;</code>
     */
    public com.google.protobuf.ByteString
        getAddressBytes() {
      Object ref = address_;
      if (ref instanceof String) {
        com.google.protobuf.ByteString b = 
            com.google.protobuf.ByteString.copyFromUtf8(
                (String) ref);
        address_ = b;
        return b;
      } else {
        return (com.google.protobuf.ByteString) ref;
      }
    }
    /**
     * <code>string address = 2;</code>
     */
    public Builder setAddress(
        String value) {
      if (value == null) {
    throw new NullPointerException();
  }
  
      address_ = value;
      onChanged();
      return this;
    }
    /**
     * <code>string address = 2;</code>
     */
    public Builder clearAddress() {
      
      address_ = getDefaultInstance().getAddress();
      onChanged();
      return this;
    }
    /**
     * <code>string address = 2;</code>
     */
    public Builder setAddressBytes(
        com.google.protobuf.ByteString value) {
      if (value == null) {
    throw new NullPointerException();
  }
  checkByteStringIsUtf8(value);
      
      address_ = value;
      onChanged();
      return this;
    }

    private Object pubKey_ = "";
    /**
     * <code>string pubKey = 3;</code>
     */
    public String getPubKey() {
      Object ref = pubKey_;
      if (!(ref instanceof String)) {
        com.google.protobuf.ByteString bs =
            (com.google.protobuf.ByteString) ref;
        String s = bs.toStringUtf8();
        pubKey_ = s;
        return s;
      } else {
        return (String) ref;
      }
    }
    /**
     * <code>string pubKey = 3;</code>
     */
    public com.google.protobuf.ByteString
        getPubKeyBytes() {
      Object ref = pubKey_;
      if (ref instanceof String) {
        com.google.protobuf.ByteString b = 
            com.google.protobuf.ByteString.copyFromUtf8(
                (String) ref);
        pubKey_ = b;
        return b;
      } else {
        return (com.google.protobuf.ByteString) ref;
      }
    }
    /**
     * <code>string pubKey = 3;</code>
     */
    public Builder setPubKey(
        String value) {
      if (value == null) {
    throw new NullPointerException();
  }
  
      pubKey_ = value;
      onChanged();
      return this;
    }
    /**
     * <code>string pubKey = 3;</code>
     */
    public Builder clearPubKey() {
      
      pubKey_ = getDefaultInstance().getPubKey();
      onChanged();
      return this;
    }
    /**
     * <code>string pubKey = 3;</code>
     */
    public Builder setPubKeyBytes(
        com.google.protobuf.ByteString value) {
      if (value == null) {
    throw new NullPointerException();
  }
  checkByteStringIsUtf8(value);
      
      pubKey_ = value;
      onChanged();
      return this;
    }
    public final Builder setUnknownFields(
        final com.google.protobuf.UnknownFieldSet unknownFields) {
      return this;
    }

    public final Builder mergeUnknownFields(
        final com.google.protobuf.UnknownFieldSet unknownFields) {
      return this;
    }


    // @@protoc_insertion_point(builder_scope:kademlia.grpc.builders.Node)
  }

  // @@protoc_insertion_point(class_scope:kademlia.grpc.builders.Node)
  private static final Node DEFAULT_INSTANCE;
  static {
    DEFAULT_INSTANCE = new Node();
  }

  public static Node getDefaultInstance() {
    return DEFAULT_INSTANCE;
  }

  private static final com.google.protobuf.Parser<Node>
      PARSER = new com.google.protobuf.AbstractParser<Node>() {
    public Node parsePartialFrom(
        com.google.protobuf.CodedInputStream input,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws com.google.protobuf.InvalidProtocolBufferException {
        return new Node(input, extensionRegistry);
    }
  };

  public static com.google.protobuf.Parser<Node> parser() {
    return PARSER;
  }

  @Override
  public com.google.protobuf.Parser<Node> getParserForType() {
    return PARSER;
  }

  public Node getDefaultInstanceForType() {
    return DEFAULT_INSTANCE;
  }

}

