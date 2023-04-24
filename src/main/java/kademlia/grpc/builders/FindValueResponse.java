// Generated by the protocol buffer compiler.  DO NOT EDIT!
// source: Kademlia.proto

package kademlia.grpc.builders;

/**
 * Protobuf type {@code kademlia.grpc.builders.FindValueResponse}
 */
public  final class FindValueResponse extends
    com.google.protobuf.GeneratedMessageV3 implements
    // @@protoc_insertion_point(message_implements:kademlia.grpc.builders.FindValueResponse)
    FindValueResponseOrBuilder {
  // Use FindValueResponse.newBuilder() to construct.
  private FindValueResponse(com.google.protobuf.GeneratedMessageV3.Builder<?> builder) {
    super(builder);
  }
  private FindValueResponse() {
    value_ = com.google.protobuf.ByteString.EMPTY;
    nodes_ = java.util.Collections.emptyList();
  }

  @Override
  public final com.google.protobuf.UnknownFieldSet
  getUnknownFields() {
    return com.google.protobuf.UnknownFieldSet.getDefaultInstance();
  }
  private FindValueResponse(
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
            Node.Builder subBuilder = null;
            if (sender_ != null) {
              subBuilder = sender_.toBuilder();
            }
            sender_ = input.readMessage(Node.parser(), extensionRegistry);
            if (subBuilder != null) {
              subBuilder.mergeFrom(sender_);
              sender_ = subBuilder.buildPartial();
            }

            break;
          }
          case 18: {

            value_ = input.readBytes();
            break;
          }
          case 26: {
            if (!((mutable_bitField0_ & 0x00000004) == 0x00000004)) {
              nodes_ = new java.util.ArrayList<Node>();
              mutable_bitField0_ |= 0x00000004;
            }
            nodes_.add(
                input.readMessage(Node.parser(), extensionRegistry));
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
      if (((mutable_bitField0_ & 0x00000004) == 0x00000004)) {
        nodes_ = java.util.Collections.unmodifiableList(nodes_);
      }
      makeExtensionsImmutable();
    }
  }
  public static final com.google.protobuf.Descriptors.Descriptor
      getDescriptor() {
    return Kademlia.internal_static_kademlia_grpc_builders_FindValueResponse_descriptor;
  }

  protected FieldAccessorTable
      internalGetFieldAccessorTable() {
    return Kademlia.internal_static_kademlia_grpc_builders_FindValueResponse_fieldAccessorTable
        .ensureFieldAccessorsInitialized(
            FindValueResponse.class, Builder.class);
  }

  private int bitField0_;
  public static final int SENDER_FIELD_NUMBER = 1;
  private Node sender_;
  /**
   * <code>.kademlia.grpc.builders.Node sender = 1;</code>
   */
  public boolean hasSender() {
    return sender_ != null;
  }
  /**
   * <code>.kademlia.grpc.builders.Node sender = 1;</code>
   */
  public Node getSender() {
    return sender_ == null ? Node.getDefaultInstance() : sender_;
  }
  /**
   * <code>.kademlia.grpc.builders.Node sender = 1;</code>
   */
  public NodeOrBuilder getSenderOrBuilder() {
    return getSender();
  }

  public static final int VALUE_FIELD_NUMBER = 2;
  private com.google.protobuf.ByteString value_;
  /**
   * <code>bytes value = 2;</code>
   */
  public com.google.protobuf.ByteString getValue() {
    return value_;
  }

  public static final int NODES_FIELD_NUMBER = 3;
  private java.util.List<Node> nodes_;
  /**
   * <code>repeated .kademlia.grpc.builders.Node nodes = 3;</code>
   */
  public java.util.List<Node> getNodesList() {
    return nodes_;
  }
  /**
   * <code>repeated .kademlia.grpc.builders.Node nodes = 3;</code>
   */
  public java.util.List<? extends NodeOrBuilder>
      getNodesOrBuilderList() {
    return nodes_;
  }
  /**
   * <code>repeated .kademlia.grpc.builders.Node nodes = 3;</code>
   */
  public int getNodesCount() {
    return nodes_.size();
  }
  /**
   * <code>repeated .kademlia.grpc.builders.Node nodes = 3;</code>
   */
  public Node getNodes(int index) {
    return nodes_.get(index);
  }
  /**
   * <code>repeated .kademlia.grpc.builders.Node nodes = 3;</code>
   */
  public NodeOrBuilder getNodesOrBuilder(
      int index) {
    return nodes_.get(index);
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
    if (sender_ != null) {
      output.writeMessage(1, getSender());
    }
    if (!value_.isEmpty()) {
      output.writeBytes(2, value_);
    }
    for (int i = 0; i < nodes_.size(); i++) {
      output.writeMessage(3, nodes_.get(i));
    }
  }

  public int getSerializedSize() {
    int size = memoizedSize;
    if (size != -1) return size;

    size = 0;
    if (sender_ != null) {
      size += com.google.protobuf.CodedOutputStream
        .computeMessageSize(1, getSender());
    }
    if (!value_.isEmpty()) {
      size += com.google.protobuf.CodedOutputStream
        .computeBytesSize(2, value_);
    }
    for (int i = 0; i < nodes_.size(); i++) {
      size += com.google.protobuf.CodedOutputStream
        .computeMessageSize(3, nodes_.get(i));
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
    if (!(obj instanceof FindValueResponse)) {
      return super.equals(obj);
    }
    FindValueResponse other = (FindValueResponse) obj;

    boolean result = true;
    result = result && (hasSender() == other.hasSender());
    if (hasSender()) {
      result = result && getSender()
          .equals(other.getSender());
    }
    result = result && getValue()
        .equals(other.getValue());
    result = result && getNodesList()
        .equals(other.getNodesList());
    return result;
  }

  @Override
  public int hashCode() {
    if (memoizedHashCode != 0) {
      return memoizedHashCode;
    }
    int hash = 41;
    hash = (19 * hash) + getDescriptor().hashCode();
    if (hasSender()) {
      hash = (37 * hash) + SENDER_FIELD_NUMBER;
      hash = (53 * hash) + getSender().hashCode();
    }
    hash = (37 * hash) + VALUE_FIELD_NUMBER;
    hash = (53 * hash) + getValue().hashCode();
    if (getNodesCount() > 0) {
      hash = (37 * hash) + NODES_FIELD_NUMBER;
      hash = (53 * hash) + getNodesList().hashCode();
    }
    hash = (29 * hash) + unknownFields.hashCode();
    memoizedHashCode = hash;
    return hash;
  }

  public static FindValueResponse parseFrom(
      java.nio.ByteBuffer data)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data);
  }
  public static FindValueResponse parseFrom(
      java.nio.ByteBuffer data,
      com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data, extensionRegistry);
  }
  public static FindValueResponse parseFrom(
      com.google.protobuf.ByteString data)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data);
  }
  public static FindValueResponse parseFrom(
      com.google.protobuf.ByteString data,
      com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data, extensionRegistry);
  }
  public static FindValueResponse parseFrom(byte[] data)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data);
  }
  public static FindValueResponse parseFrom(
      byte[] data,
      com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data, extensionRegistry);
  }
  public static FindValueResponse parseFrom(java.io.InputStream input)
      throws java.io.IOException {
    return com.google.protobuf.GeneratedMessageV3
        .parseWithIOException(PARSER, input);
  }
  public static FindValueResponse parseFrom(
      java.io.InputStream input,
      com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws java.io.IOException {
    return com.google.protobuf.GeneratedMessageV3
        .parseWithIOException(PARSER, input, extensionRegistry);
  }
  public static FindValueResponse parseDelimitedFrom(java.io.InputStream input)
      throws java.io.IOException {
    return com.google.protobuf.GeneratedMessageV3
        .parseDelimitedWithIOException(PARSER, input);
  }
  public static FindValueResponse parseDelimitedFrom(
      java.io.InputStream input,
      com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws java.io.IOException {
    return com.google.protobuf.GeneratedMessageV3
        .parseDelimitedWithIOException(PARSER, input, extensionRegistry);
  }
  public static FindValueResponse parseFrom(
      com.google.protobuf.CodedInputStream input)
      throws java.io.IOException {
    return com.google.protobuf.GeneratedMessageV3
        .parseWithIOException(PARSER, input);
  }
  public static FindValueResponse parseFrom(
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
  public static Builder newBuilder(FindValueResponse prototype) {
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
   * Protobuf type {@code kademlia.grpc.builders.FindValueResponse}
   */
  public static final class Builder extends
      com.google.protobuf.GeneratedMessageV3.Builder<Builder> implements
      // @@protoc_insertion_point(builder_implements:kademlia.grpc.builders.FindValueResponse)
      FindValueResponseOrBuilder {
    public static final com.google.protobuf.Descriptors.Descriptor
        getDescriptor() {
      return Kademlia.internal_static_kademlia_grpc_builders_FindValueResponse_descriptor;
    }

    protected FieldAccessorTable
        internalGetFieldAccessorTable() {
      return Kademlia.internal_static_kademlia_grpc_builders_FindValueResponse_fieldAccessorTable
          .ensureFieldAccessorsInitialized(
              FindValueResponse.class, Builder.class);
    }

    // Construct using kademlia.grpc.builders.FindValueResponse.newBuilder()
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
        getNodesFieldBuilder();
      }
    }
    public Builder clear() {
      super.clear();
      if (senderBuilder_ == null) {
        sender_ = null;
      } else {
        sender_ = null;
        senderBuilder_ = null;
      }
      value_ = com.google.protobuf.ByteString.EMPTY;

      if (nodesBuilder_ == null) {
        nodes_ = java.util.Collections.emptyList();
        bitField0_ = (bitField0_ & ~0x00000004);
      } else {
        nodesBuilder_.clear();
      }
      return this;
    }

    public com.google.protobuf.Descriptors.Descriptor
        getDescriptorForType() {
      return Kademlia.internal_static_kademlia_grpc_builders_FindValueResponse_descriptor;
    }

    public FindValueResponse getDefaultInstanceForType() {
      return FindValueResponse.getDefaultInstance();
    }

    public FindValueResponse build() {
      FindValueResponse result = buildPartial();
      if (!result.isInitialized()) {
        throw newUninitializedMessageException(result);
      }
      return result;
    }

    public FindValueResponse buildPartial() {
      FindValueResponse result = new FindValueResponse(this);
      int from_bitField0_ = bitField0_;
      int to_bitField0_ = 0;
      if (senderBuilder_ == null) {
        result.sender_ = sender_;
      } else {
        result.sender_ = senderBuilder_.build();
      }
      result.value_ = value_;
      if (nodesBuilder_ == null) {
        if (((bitField0_ & 0x00000004) == 0x00000004)) {
          nodes_ = java.util.Collections.unmodifiableList(nodes_);
          bitField0_ = (bitField0_ & ~0x00000004);
        }
        result.nodes_ = nodes_;
      } else {
        result.nodes_ = nodesBuilder_.build();
      }
      result.bitField0_ = to_bitField0_;
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
      if (other instanceof FindValueResponse) {
        return mergeFrom((FindValueResponse)other);
      } else {
        super.mergeFrom(other);
        return this;
      }
    }

    public Builder mergeFrom(FindValueResponse other) {
      if (other == FindValueResponse.getDefaultInstance()) return this;
      if (other.hasSender()) {
        mergeSender(other.getSender());
      }
      if (other.getValue() != com.google.protobuf.ByteString.EMPTY) {
        setValue(other.getValue());
      }
      if (nodesBuilder_ == null) {
        if (!other.nodes_.isEmpty()) {
          if (nodes_.isEmpty()) {
            nodes_ = other.nodes_;
            bitField0_ = (bitField0_ & ~0x00000004);
          } else {
            ensureNodesIsMutable();
            nodes_.addAll(other.nodes_);
          }
          onChanged();
        }
      } else {
        if (!other.nodes_.isEmpty()) {
          if (nodesBuilder_.isEmpty()) {
            nodesBuilder_.dispose();
            nodesBuilder_ = null;
            nodes_ = other.nodes_;
            bitField0_ = (bitField0_ & ~0x00000004);
            nodesBuilder_ = 
              com.google.protobuf.GeneratedMessageV3.alwaysUseFieldBuilders ?
                 getNodesFieldBuilder() : null;
          } else {
            nodesBuilder_.addAllMessages(other.nodes_);
          }
        }
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
      FindValueResponse parsedMessage = null;
      try {
        parsedMessage = PARSER.parsePartialFrom(input, extensionRegistry);
      } catch (com.google.protobuf.InvalidProtocolBufferException e) {
        parsedMessage = (FindValueResponse) e.getUnfinishedMessage();
        throw e.unwrapIOException();
      } finally {
        if (parsedMessage != null) {
          mergeFrom(parsedMessage);
        }
      }
      return this;
    }
    private int bitField0_;

    private Node sender_ = null;
    private com.google.protobuf.SingleFieldBuilderV3<
        Node, Node.Builder, NodeOrBuilder> senderBuilder_;
    /**
     * <code>.kademlia.grpc.builders.Node sender = 1;</code>
     */
    public boolean hasSender() {
      return senderBuilder_ != null || sender_ != null;
    }
    /**
     * <code>.kademlia.grpc.builders.Node sender = 1;</code>
     */
    public Node getSender() {
      if (senderBuilder_ == null) {
        return sender_ == null ? Node.getDefaultInstance() : sender_;
      } else {
        return senderBuilder_.getMessage();
      }
    }
    /**
     * <code>.kademlia.grpc.builders.Node sender = 1;</code>
     */
    public Builder setSender(Node value) {
      if (senderBuilder_ == null) {
        if (value == null) {
          throw new NullPointerException();
        }
        sender_ = value;
        onChanged();
      } else {
        senderBuilder_.setMessage(value);
      }

      return this;
    }
    /**
     * <code>.kademlia.grpc.builders.Node sender = 1;</code>
     */
    public Builder setSender(
        Node.Builder builderForValue) {
      if (senderBuilder_ == null) {
        sender_ = builderForValue.build();
        onChanged();
      } else {
        senderBuilder_.setMessage(builderForValue.build());
      }

      return this;
    }
    /**
     * <code>.kademlia.grpc.builders.Node sender = 1;</code>
     */
    public Builder mergeSender(Node value) {
      if (senderBuilder_ == null) {
        if (sender_ != null) {
          sender_ =
            Node.newBuilder(sender_).mergeFrom(value).buildPartial();
        } else {
          sender_ = value;
        }
        onChanged();
      } else {
        senderBuilder_.mergeFrom(value);
      }

      return this;
    }
    /**
     * <code>.kademlia.grpc.builders.Node sender = 1;</code>
     */
    public Builder clearSender() {
      if (senderBuilder_ == null) {
        sender_ = null;
        onChanged();
      } else {
        sender_ = null;
        senderBuilder_ = null;
      }

      return this;
    }
    /**
     * <code>.kademlia.grpc.builders.Node sender = 1;</code>
     */
    public Node.Builder getSenderBuilder() {
      
      onChanged();
      return getSenderFieldBuilder().getBuilder();
    }
    /**
     * <code>.kademlia.grpc.builders.Node sender = 1;</code>
     */
    public NodeOrBuilder getSenderOrBuilder() {
      if (senderBuilder_ != null) {
        return senderBuilder_.getMessageOrBuilder();
      } else {
        return sender_ == null ?
            Node.getDefaultInstance() : sender_;
      }
    }
    /**
     * <code>.kademlia.grpc.builders.Node sender = 1;</code>
     */
    private com.google.protobuf.SingleFieldBuilderV3<
        Node, Node.Builder, NodeOrBuilder>
        getSenderFieldBuilder() {
      if (senderBuilder_ == null) {
        senderBuilder_ = new com.google.protobuf.SingleFieldBuilderV3<
            Node, Node.Builder, NodeOrBuilder>(
                getSender(),
                getParentForChildren(),
                isClean());
        sender_ = null;
      }
      return senderBuilder_;
    }

    private com.google.protobuf.ByteString value_ = com.google.protobuf.ByteString.EMPTY;
    /**
     * <code>bytes value = 2;</code>
     */
    public com.google.protobuf.ByteString getValue() {
      return value_;
    }
    /**
     * <code>bytes value = 2;</code>
     */
    public Builder setValue(com.google.protobuf.ByteString value) {
      if (value == null) {
    throw new NullPointerException();
  }
  
      value_ = value;
      onChanged();
      return this;
    }
    /**
     * <code>bytes value = 2;</code>
     */
    public Builder clearValue() {
      
      value_ = getDefaultInstance().getValue();
      onChanged();
      return this;
    }

    private java.util.List<Node> nodes_ =
      java.util.Collections.emptyList();
    private void ensureNodesIsMutable() {
      if (!((bitField0_ & 0x00000004) == 0x00000004)) {
        nodes_ = new java.util.ArrayList<Node>(nodes_);
        bitField0_ |= 0x00000004;
       }
    }

    private com.google.protobuf.RepeatedFieldBuilderV3<
        Node, Node.Builder, NodeOrBuilder> nodesBuilder_;

    /**
     * <code>repeated .kademlia.grpc.builders.Node nodes = 3;</code>
     */
    public java.util.List<Node> getNodesList() {
      if (nodesBuilder_ == null) {
        return java.util.Collections.unmodifiableList(nodes_);
      } else {
        return nodesBuilder_.getMessageList();
      }
    }
    /**
     * <code>repeated .kademlia.grpc.builders.Node nodes = 3;</code>
     */
    public int getNodesCount() {
      if (nodesBuilder_ == null) {
        return nodes_.size();
      } else {
        return nodesBuilder_.getCount();
      }
    }
    /**
     * <code>repeated .kademlia.grpc.builders.Node nodes = 3;</code>
     */
    public Node getNodes(int index) {
      if (nodesBuilder_ == null) {
        return nodes_.get(index);
      } else {
        return nodesBuilder_.getMessage(index);
      }
    }
    /**
     * <code>repeated .kademlia.grpc.builders.Node nodes = 3;</code>
     */
    public Builder setNodes(
        int index, Node value) {
      if (nodesBuilder_ == null) {
        if (value == null) {
          throw new NullPointerException();
        }
        ensureNodesIsMutable();
        nodes_.set(index, value);
        onChanged();
      } else {
        nodesBuilder_.setMessage(index, value);
      }
      return this;
    }
    /**
     * <code>repeated .kademlia.grpc.builders.Node nodes = 3;</code>
     */
    public Builder setNodes(
        int index, Node.Builder builderForValue) {
      if (nodesBuilder_ == null) {
        ensureNodesIsMutable();
        nodes_.set(index, builderForValue.build());
        onChanged();
      } else {
        nodesBuilder_.setMessage(index, builderForValue.build());
      }
      return this;
    }
    /**
     * <code>repeated .kademlia.grpc.builders.Node nodes = 3;</code>
     */
    public Builder addNodes(Node value) {
      if (nodesBuilder_ == null) {
        if (value == null) {
          throw new NullPointerException();
        }
        ensureNodesIsMutable();
        nodes_.add(value);
        onChanged();
      } else {
        nodesBuilder_.addMessage(value);
      }
      return this;
    }
    /**
     * <code>repeated .kademlia.grpc.builders.Node nodes = 3;</code>
     */
    public Builder addNodes(
        int index, Node value) {
      if (nodesBuilder_ == null) {
        if (value == null) {
          throw new NullPointerException();
        }
        ensureNodesIsMutable();
        nodes_.add(index, value);
        onChanged();
      } else {
        nodesBuilder_.addMessage(index, value);
      }
      return this;
    }
    /**
     * <code>repeated .kademlia.grpc.builders.Node nodes = 3;</code>
     */
    public Builder addNodes(
        Node.Builder builderForValue) {
      if (nodesBuilder_ == null) {
        ensureNodesIsMutable();
        nodes_.add(builderForValue.build());
        onChanged();
      } else {
        nodesBuilder_.addMessage(builderForValue.build());
      }
      return this;
    }
    /**
     * <code>repeated .kademlia.grpc.builders.Node nodes = 3;</code>
     */
    public Builder addNodes(
        int index, Node.Builder builderForValue) {
      if (nodesBuilder_ == null) {
        ensureNodesIsMutable();
        nodes_.add(index, builderForValue.build());
        onChanged();
      } else {
        nodesBuilder_.addMessage(index, builderForValue.build());
      }
      return this;
    }
    /**
     * <code>repeated .kademlia.grpc.builders.Node nodes = 3;</code>
     */
    public Builder addAllNodes(
        Iterable<? extends Node> values) {
      if (nodesBuilder_ == null) {
        ensureNodesIsMutable();
        com.google.protobuf.AbstractMessageLite.Builder.addAll(
            values, nodes_);
        onChanged();
      } else {
        nodesBuilder_.addAllMessages(values);
      }
      return this;
    }
    /**
     * <code>repeated .kademlia.grpc.builders.Node nodes = 3;</code>
     */
    public Builder clearNodes() {
      if (nodesBuilder_ == null) {
        nodes_ = java.util.Collections.emptyList();
        bitField0_ = (bitField0_ & ~0x00000004);
        onChanged();
      } else {
        nodesBuilder_.clear();
      }
      return this;
    }
    /**
     * <code>repeated .kademlia.grpc.builders.Node nodes = 3;</code>
     */
    public Builder removeNodes(int index) {
      if (nodesBuilder_ == null) {
        ensureNodesIsMutable();
        nodes_.remove(index);
        onChanged();
      } else {
        nodesBuilder_.remove(index);
      }
      return this;
    }
    /**
     * <code>repeated .kademlia.grpc.builders.Node nodes = 3;</code>
     */
    public Node.Builder getNodesBuilder(
        int index) {
      return getNodesFieldBuilder().getBuilder(index);
    }
    /**
     * <code>repeated .kademlia.grpc.builders.Node nodes = 3;</code>
     */
    public NodeOrBuilder getNodesOrBuilder(
        int index) {
      if (nodesBuilder_ == null) {
        return nodes_.get(index);  } else {
        return nodesBuilder_.getMessageOrBuilder(index);
      }
    }
    /**
     * <code>repeated .kademlia.grpc.builders.Node nodes = 3;</code>
     */
    public java.util.List<? extends NodeOrBuilder>
         getNodesOrBuilderList() {
      if (nodesBuilder_ != null) {
        return nodesBuilder_.getMessageOrBuilderList();
      } else {
        return java.util.Collections.unmodifiableList(nodes_);
      }
    }
    /**
     * <code>repeated .kademlia.grpc.builders.Node nodes = 3;</code>
     */
    public Node.Builder addNodesBuilder() {
      return getNodesFieldBuilder().addBuilder(
          Node.getDefaultInstance());
    }
    /**
     * <code>repeated .kademlia.grpc.builders.Node nodes = 3;</code>
     */
    public Node.Builder addNodesBuilder(
        int index) {
      return getNodesFieldBuilder().addBuilder(
          index, Node.getDefaultInstance());
    }
    /**
     * <code>repeated .kademlia.grpc.builders.Node nodes = 3;</code>
     */
    public java.util.List<Node.Builder>
         getNodesBuilderList() {
      return getNodesFieldBuilder().getBuilderList();
    }
    private com.google.protobuf.RepeatedFieldBuilderV3<
        Node, Node.Builder, NodeOrBuilder>
        getNodesFieldBuilder() {
      if (nodesBuilder_ == null) {
        nodesBuilder_ = new com.google.protobuf.RepeatedFieldBuilderV3<
            Node, Node.Builder, NodeOrBuilder>(
                nodes_,
                ((bitField0_ & 0x00000004) == 0x00000004),
                getParentForChildren(),
                isClean());
        nodes_ = null;
      }
      return nodesBuilder_;
    }
    public final Builder setUnknownFields(
        final com.google.protobuf.UnknownFieldSet unknownFields) {
      return this;
    }

    public final Builder mergeUnknownFields(
        final com.google.protobuf.UnknownFieldSet unknownFields) {
      return this;
    }


    // @@protoc_insertion_point(builder_scope:kademlia.grpc.builders.FindValueResponse)
  }

  // @@protoc_insertion_point(class_scope:kademlia.grpc.builders.FindValueResponse)
  private static final FindValueResponse DEFAULT_INSTANCE;
  static {
    DEFAULT_INSTANCE = new FindValueResponse();
  }

  public static FindValueResponse getDefaultInstance() {
    return DEFAULT_INSTANCE;
  }

  private static final com.google.protobuf.Parser<FindValueResponse>
      PARSER = new com.google.protobuf.AbstractParser<FindValueResponse>() {
    public FindValueResponse parsePartialFrom(
        com.google.protobuf.CodedInputStream input,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws com.google.protobuf.InvalidProtocolBufferException {
        return new FindValueResponse(input, extensionRegistry);
    }
  };

  public static com.google.protobuf.Parser<FindValueResponse> parser() {
    return PARSER;
  }

  @Override
  public com.google.protobuf.Parser<FindValueResponse> getParserForType() {
    return PARSER;
  }

  public FindValueResponse getDefaultInstanceForType() {
    return DEFAULT_INSTANCE;
  }

}

